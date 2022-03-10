package de.gma.gamma.lexer

import de.gma.gamma.GammaToken
import de.gma.gamma.GammaTokenType
import de.gma.gamma.Position
import java.lang.Character.MIN_VALUE as nullChar

class GammaLexer(val source: String) {

    val length = source.length

    var pos = 0
    var line = 0
    var col = 0

    var char = nullChar
    var peekChar = nullChar

    val tokens = mutableListOf<GammaToken>()

    init {
        if (source.isNotBlank())
            char = source[0]

        if (source.length>1)
            peekChar = source[1]
    }

    fun tokens(): List<GammaToken> {
        while (char != nullChar) {

            if (isFirstNumberChar(char, peekChar)) {
                tokens.add(parseNumber())
            }
            else if (isParens(char)) {
                tokens.add(parseParens())
            }
            else if (char == DQUOTE)
                tokens.add(parseDQuoteString())
            else {
                tokens.add(errorToken())
                next()
            }

        }
        return tokens
    }

    // ============= different parser methode ================
    // A Parser takes the first character as "char" and continues until the
    // token is finished
    // char is than the next character behind the token
    // return the token

    private fun parseDQuoteString() : GammaToken {
        val start = position()
        val content = StringBuilder()

        // consume the "
        next()

        fun isStillInString() = char != DQUOTE && char != RETURN && char != nullChar

        while (isStillInString()) {
            if (char == ESC) {
                next() // for ESC char
                when(char) {
                    'n' -> {
                        content.append("\n")
                    }
                    't' -> {
                        content.append("\t")
                    }
                    RETURN -> {
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

        val type = if (char != DQUOTE) GammaTokenType.ERROR else GammaTokenType.STRING
        val end = position()

        next()
        return GammaToken(
            type = type,
            content = content.toString(),
            start = start,
            end = end
        )
    }

    private fun parseParens() : GammaToken {
        val ret = GammaToken(
            type = GammaTokenType.PARENS,
            content = char.toString(),
            start = position(),
            end = position()
        )
        next()
        return ret
    }

    private fun parseNumber() : GammaToken {
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
        return GammaToken(
            type = GammaTokenType.NUMBER,
            content = content.toString(),
            start = start,
            end = end
        )
    }

    // TODO wieder ausbauen wenn fertig, es gibt keine fehlerhaften Tokens
    private fun errorToken() =
        GammaToken(
            type = GammaTokenType.ERROR,
            content = char.toString(),
            start = position(),
            end = position()
        )

    private fun position() =
        Position(pos, col, line)

    /**
     * Geht zum nächsten Zeichen. Setzt chat und peekChar mit den Zeichen, sofern noch Vorrat
     * vorhanden ist.
     * Setzt nullChar ansonsten.
     */
    private fun next() {
        pos++
        if (char == RETURN) {
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

            if (pos < length-1)
                peekChar = source[pos+1]
            else
                peekChar = nullChar
        }
    }

}
