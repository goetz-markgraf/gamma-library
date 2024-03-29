package de.gma.gamma.parser

class Lexer(
    private val source: String,
    private val sourceName: String = "Script"
) {

    private val length = source.length

    private var pos = 0
    private var line = 0
    private var col = 0

    private var char = nullChar
    private var peekChar = nullChar
    private var peekPeekChar = nullChar

    init {
        if (source.isNotBlank())
            char = source[0]

        if (source.length > 1)
            peekChar = source[1]

        if (source.length > 2)
            peekPeekChar = source[2]
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
        if (isStartOfLineComment(char)) {
            skipComment()
            skipWhitespace()
        }

        return when {
            isEof(char) -> eof()

            isExpressionEndingChar(char) -> parseExpressionEnding()

            isStartOfString(char) -> parseString()

            isStartOfDocumentation(char) -> parseDocumentation()

            isStartOfNumber(char, peekChar) -> parseNumber()

            isVoid(char, peekChar) -> parseVoid()

            isTernaryCharacter(char) -> parseTernaryCharacter()

            isStartOfProperty(char, peekChar) -> parseProperty()

            isStartOfIdentifier(char) -> parseIdentifier()

            isOperatorChar(char) -> parseOperator()

            isStartOfFunctionOperator(char, peekChar, peekPeekChar) -> parseOperatorAsIdentifier()

            isParens(char) -> parseParens()

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

    private fun parseTernaryCharacter(): Token {
        val ret = Token(
            type = TokenType.TERNARY,
            content = char.toString(),
            sourceName = sourceName,
            start = position(),
            end = position()
        )
        next()
        return ret
    }

    private fun parseExpressionEnding(): Token {
        val string = position()
        val content = StringBuilder()
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


    private fun parseVoid(): Token {
        val start = position()
        next()
        val end = position()
        next()
        return Token(
            type = TokenType.VOID,
            content = "$CH_VOID1$CH_VOID2",
            sourceName = sourceName,
            start = start,
            end = end
        )
    }

    private fun parseOperatorAsIdentifier(): Token {
        next() // Überlesen der öffenenden Klammer
        val ret = parseOperator()
        return if (char == CH_RPARENS) {
            next() // Überlesen der schließenden Klammer
            ret.copy(type = TokenType.OP_AS_ID)
        } else {
            ret.copy(type = TokenType.ERROR)
        }
    }


    private fun parseOperator(): Token {
        val start = position()
        val content = StringBuilder()

        var end = position()
        while (isOperatorChar(char)) {
            content.append(char)
            end = position()
            next()
        }

        val c = content.toString()
        return Token(
            type = if (c == ":") TokenType.TERNARY else TokenType.OP,
            content = c,
            sourceName = sourceName,
            start = start,
            end = end
        )
    }


    private fun parseProperty(): Token {
        val start = position()
        next()

        val ret = parseIdentifier()
        return Token(
            type = TokenType.PROPERTY,
            start = start,
            end = ret.end,
            content = ret.content,
            sourceName = ret.sourceName
        )
    }


    private fun parseIdentifier(): Token {
        val start = position()
        val contentBuffer = StringBuilder()

        var end = position()

        while (isIdentifierChar(char)) {
            val ending = isEndOfIdentifier(char)
            contentBuffer.append(char)
            end = position()
            next()

            if (ending)
                break
        }

        val id = when (contentBuffer.toString()) {
            "let" -> TokenType.LET
            "set" -> TokenType.SET
            "type" -> TokenType.TYPE
            "module" -> TokenType.MODULE
            "true" -> TokenType.TRUE
            "false" -> TokenType.FALSE
            "match" -> TokenType.MATCH
            "with" -> TokenType.WITH
            else -> TokenType.ID
        }

        return Token(
            type = id,
            content = contentBuffer.toString(),
            sourceName = sourceName,
            start = start,
            end = end
        )
    }


    private fun parseDocumentation(): Token {
        val start = position()
        next()

        val content = StringBuilder()
        fun isStillInDocumentation() = char != CH_APOSTR && char != nullChar

        while (isStillInDocumentation()) {
            if (char == CH_ESC && peekChar == CH_APOSTR) {
                next()
                content.append(CH_APOSTR)
            } else {
                content.append(char)
            }
            next()
        }

        val type = if (char == CH_APOSTR) TokenType.DOCUMENTATION else TokenType.ERROR

        //consume the trailing aspostrophy
        next()

        return Token(
            type = type,
            sourceName = sourceName,
            content = content.toString(),
            start = start,
            end = position()
        )
    }


    fun parseString(startWithQuote: Boolean = true): Token {
        val start = position()
        val content = StringBuilder()

        // consume the " if needed
        if (startWithQuote)
            next()

        fun isStillInString() =
            char != CH_QUOTE && char != CH_NEWLINE && char != nullChar && !(char == CH_DOLLAR && peekChar == '(')

        while (isStillInString()) {
            if (char == CH_ESC) {
                next() // for ESC char
                when (char) {
                    'n' -> content.append("\n")
                    't' -> content.append("\t")
                    CH_NEWLINE -> { /* do nothing */
                    }

                    else -> content.append(char)
                }
            } else {
                content.append(char)
            }
            next()
        }

        val type = when (char) {
            CH_QUOTE -> TokenType.STRING
            CH_DOLLAR -> TokenType.STRING_INTERPOLATION
            else -> TokenType.ERROR
        }
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
        val open = "({[".contains(char)
        val ret = Token(
            type = if (open) TokenType.OPEN_PARENS else TokenType.CLOSE_PARENS,
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

        fun isStillNumber() =
            isNumberChar(char) || (position() == start && char == CH_MINUS) || (!decimal && char == CH_DOT)

        while (isStillNumber()) {
            content.append(char)
            if (char == CH_DOT)
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

        // \r (part of newline on Windows machines) will not be counted but silently ignored in col/line
        if (char != '\r') {
            if (char == CH_NEWLINE) {
                line++
                col = 0
            } else {
                col++
            }
        }

        if (pos >= length) {
            char = nullChar
            peekChar = nullChar
            peekPeekChar = nullChar
        } else {
            char = peekChar

            peekChar = if (pos < length - 1)
                source[pos + 1]
            else
                nullChar

            if (pos < length - 2)
                peekPeekChar = source[pos + 2]
            else
                peekPeekChar = nullChar
        }
    }

    private fun skipWhitespace() {
        fun isGammaWhitespace() = char != '\t' && isWhitespace(char)
        while (isGammaWhitespace()) {
            next()
        }
    }

    private fun skipComment() {
        while (char != CH_NEWLINE && char != nullChar) {
            next()
        }
    }
}
