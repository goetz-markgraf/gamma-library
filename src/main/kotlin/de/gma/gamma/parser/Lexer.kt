package de.gma.gamma.parser

import de.gma.gamma.Token
import de.gma.gamma.TokenType
import de.gma.gamma.Position
import java.lang.Character.MIN_VALUE as nullChar

class Lexer(
    private val source: String,
    private val sourceName: String
) {

    private val length = source.length

    private var pos = 0
    private var line = 0
    private var col = 0

    private var char = nullChar
    private var peekChar = nullChar

    init {
        if (source.isNotBlank())
            char = source[0]

        if (source.length > 1)
            peekChar = source[1]
    }

    private fun eof() =
        Token(
            type = TokenType.EOF,
            sourceName = sourceName,
            content = "",
            start = position(),
            end = position()
        )

    /**
     * Return the next token for the given input.
     * @return the Token. If the end of file is reached, an EOF-Token is always returned
     * @see TokenType
     */
    fun nextToken(): Token {
        skipWhitespace()

        return when {
            isStartOfNumber(char, peekChar) -> parseNumber()

            isStartOfIdentifier(char) -> parseIdentifier()

            isTickedIdentifierChar(char, peekChar) -> parseTickedIdentifier()

            isOperatorChar(char) -> parseOperator()

            isTickedOperatorChar(char, peekChar) -> parseTickedOperator()

            isUnit(char, peekChar) -> parseUnit()

            isParens(char) -> parseParens()

            isStartOfString(char) -> parseDQuoteString()

            isExpressionEndingChar(char) -> parseExpressionEnding()

            isEof(char) -> eof()

            else -> {
                val ret = errorToken()
                next()
                ret
            }
        }
    }

    // ============= different parser methode ================
    // A Parser takes the first character as "char" and continues until the
    // token is finished
    // char is than the next character behind the token
    // return the token


    private fun parseExpressionEnding(): Token {
        val string = position()
        val content = StringBuffer()
        var end = position()

        while (isExpressionEndingChar(char)) {
            content.append(char)
            end = position()
            next()
        }

        return Token(
            type = TokenType.EXEND,
            content = content.toString(),
            sourceName = sourceName,
            start = string,
            end = end
        )
    }


    private fun parseUnit(): Token {
        val start = position()
        next()
        val end = position()
        next()
        return Token(
            type = TokenType.UNIT,
            content = "$UNIT1$UNIT2",
            sourceName = sourceName,
            start = start,
            end = end
        )
    }

    private fun parseTickedIdentifier(): Token {
        next() // Wir brauchen den Tick nicht
        val ret = parseIdentifier()
        return if (char == TICK || char == BACKTICK) {
            next()
            ret.copy(type = TokenType.TID)
        } else {
            ret.copy(type = TokenType.ERROR)
        }
    }

    private fun parseTickedOperator(): Token {
        next() // Wir brauchen den Tick nicht
        val ret = parseOperator()
        return if (char == TICK || char == BACKTICK) {
            next()
            ret.copy(type = TokenType.TOP)
        } else {
            ret.copy(type = TokenType.ERROR)
        }
    }


    private fun parseOperator(): Token {
        val start = position()
        val content = StringBuffer()

        var end = position()
        while (isOperatorChar(char)) {
            content.append(char)
            end = position()
            next()
        }

        return Token(
            type = TokenType.OP,
            content = content.toString(),
            sourceName = sourceName,
            start = start,
            end = end
        )
    }


    private fun parseIdentifier(): Token {
        val start = position()
        val content = StringBuilder()

        var end = position()

        fun isStillIdentifier() =
            isIdentifierChar(char) || (isValidSpecialIdentifierNonEndingChar(char) && isIdentifierChar(peekChar))

        while (isStillIdentifier()) {
            content.append(char)
            end = position()
            next()
        }

        return Token(
            type = TokenType.ID,
            content = content.toString(),
            sourceName = sourceName,
            start = start,
            end = end
        )
    }


    private fun parseDQuoteString(): Token {
        val start = position()
        val content = StringBuilder()

        // consume the "
        next()

        fun isStillInString() = char != QUOTE && char != NEWLINE && char != nullChar

        while (isStillInString()) {
            if (char == ESC) {
                next() // for ESC char
                when (char) {
                    'n' -> {
                        content.append("\n")
                    }
                    't' -> {
                        content.append("\t")
                    }
                    NEWLINE -> {
                        // do nothing
                    }
                    else -> {
                        content.append(char)
                    }
                }
            } else {
                content.append(char)
            }
            next()
        }

        val type = if (char != QUOTE) TokenType.ERROR else TokenType.STRING
        val end = position()

        next()
        return Token(
            type = type,
            content = content.toString(),
            sourceName = sourceName,
            start = start,
            end = end
        )
    }


    private fun parseParens(): Token {
        val ret = Token(
            type = TokenType.PARENS,
            content = char.toString(),
            sourceName = sourceName,
            start = position(),
            end = position()
        )
        next()
        return ret
    }


    private fun parseNumber(): Token {
        val start = position()
        val content = StringBuilder()
        var decimal = false
        var end = position()

        fun isStillNumber() = isNumberChar(char) || (position() == start && char == MINUS) || (!decimal && char == DOT)

        while (isStillNumber()) {
            content.append(char)
            if (char == DOT)
                decimal = true
            end = position()
            next()
        }
        return Token(
            type = TokenType.NUMBER,
            content = content.toString(),
            sourceName = sourceName,
            start = start,
            end = end
        )
    }


    // ============= helper functions ================


    private fun errorToken() =
        Token(
            type = TokenType.ERROR,
            content = char.toString(),
            sourceName = sourceName,
            start = position(),
            end = position()
        )

    private fun position() =
        Position(pos, col, line)

    /*
     * fetches next character. sets "char" and "peekChar" with characters if there are still
     * some left.
     * otherwise, set "nullChar"
     */
    private fun next() {
        pos++
        if (char == NEWLINE) {
            line++
            col = 0
        } else {
            col++
        }

        if (pos >= length) {
            char = nullChar
            peekChar = nullChar
        } else {
            char = peekChar

            if (pos < length - 1)
                peekChar = source[pos + 1]
            else
                peekChar = nullChar
        }
    }

    private fun skipWhitespace() {
        while (isWhitespace(char) || isStartOfComment(char)) {
            if (isStartOfComment(char)) {
                skiptComment()
            } else {
                next()
            }
        }
    }

    private fun skiptComment() {
        while (char != NEWLINE && char != nullChar)
            next()
    }
}
