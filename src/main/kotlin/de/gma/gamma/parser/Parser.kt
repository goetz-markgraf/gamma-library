package de.gma.gamma.parser

import de.gma.gamma.Token
import de.gma.gamma.TokenType.*
import de.gma.gamma.datatypes.FloatValue
import de.gma.gamma.datatypes.IntegerValue
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value

class Parser(private val lexer: Lexer) {
    var token: Token = lexer.nextToken()

    fun nextExpression(): Value? {
        var value: Value? = null

        if (token.type != EOF) {
            skipWhitespace()

            when (token.type) {
                NUMBER -> value = parseNumber()

                STRING -> value = parseString()
            }

        }

        return value
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
