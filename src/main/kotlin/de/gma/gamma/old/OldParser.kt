package de.gma.gamma.old

import de.gma.gamma.old.olddatatypes.*
import de.gma.gamma.parser.Lexer
import de.gma.gamma.parser.Token
import de.gma.gamma.parser.TokenType.*

class OldParser(private val lexer: Lexer) {
    var token: Token = lexer.nextToken()
    var peekToken: Token = lexer.nextToken()

    fun nextExpression(col: Int): Value {

        skipWhitespace()

        return when (token.type) {
            NUMBER -> parseNumber()

            STRING -> parseString()

            ID, OP_AS_ID -> parseIdentifierOrFunctionCall(col)

            else -> throw RuntimeException("NOT YET IMPLEMENTED")
        }


    }

    // ======= parse functions ==========


    private fun parseIdentifierOrFunctionCall(col: Int): Value {
        val id = parseIdentifier()

        var cont = true
        val call = mutableListOf<Value>(id)


        while (cont) {
            when (token.type) {
                UNIT -> return FunctionCall(listOf(id), id.sourceName, id.start, token.end)
                NUMBER -> call.add(parseNumber())
                STRING -> call.add(parseString())
                ID, OP_AS_ID -> call.add(parseIdentifier())
                else -> cont = false
            }
        }

        return if (call.size == 1)
            id
        else
            FunctionCall(call, id.sourceName, id.start, call.last().end)
    }

    private fun parseIdentifier(): Value {
        val content = token.content
        val ret = Identifier(content, token.sourceName, token.start, token.end)
        next()
        return ret
    }


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
        token = peekToken
        peekToken = lexer.nextToken()
    }
}
