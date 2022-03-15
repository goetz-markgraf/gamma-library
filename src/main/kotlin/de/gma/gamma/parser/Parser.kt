package de.gma.gamma.parser

import de.gma.gamma.Token
import de.gma.gamma.TokenType.*
import de.gma.gamma.datatypes.FloatValue
import de.gma.gamma.datatypes.IntegerValue
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value

class Parser(private val lexer: Lexer) {
    var token: Token = lexer.nextToken()

    fun nextExpression(col: Int): Value? {

        skipWhitespace()

        return when (token.type) {
            NUMBER -> parseNumber()

            STRING -> parseString()

            else -> throw RuntimeException("NOT YET IMPLEMENTED")
        }


    }

    // ======= parse functions ==========


    private fun parseString(): Value {
        val content = token.content

        val ret = StringValue(content, token.sourceName, token.start, token.end)
        next()
        return ret
    }


    private fun parseNumber(): Value {
        val numString = token.content

        return if (numString.contains('.'))
            parseFloat()
        else
            parseInt()
    }

    private fun parseInt(): IntegerValue {
        val num = token.content.toIntOrNull()
            ?: throw java.lang.IllegalArgumentException("${token.content} is not an integer")
        val ret = IntegerValue(num, token.sourceName, token.start, token.end)
        next()
        return ret
    }

    private fun parseFloat(): FloatValue {
        val num = token.content.toDoubleOrNull()
            ?: throw java.lang.IllegalArgumentException("${token.content} is not a float number")
        val ret = FloatValue(num, token.sourceName, token.start, token.end)
        next()
        return ret
    }


    // ======= Helper Function ==========

    private fun skipWhitespace() {
        while (token.type == EXEND)
            next()
    }

    private fun next() {
        token = lexer.nextToken()
    }
}
