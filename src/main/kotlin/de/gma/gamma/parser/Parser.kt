package de.gma.gamma.parser

import de.gma.gamma.datatypes.*
import de.gma.gamma.datatypes.expressions.GIfExpression
import de.gma.gamma.datatypes.expressions.GLetExpression
import de.gma.gamma.datatypes.expressions.GOperation
import de.gma.gamma.parser.TokenType.*


class Parser(
    source: String,
    private val sourceName: String
) {

    private val lexer = Lexer(source, sourceName)
    private lateinit var currToken: Token
    private lateinit var currType: TokenType
    private lateinit var currStart: Position
    private lateinit var currEnd: Position

    init {
        nextToken()
    }

    /**
     * returns the next expression that is not yet read from the input source
     */
    fun nextExpression(col: Int): GValue? {
        val minCol = if (col < 0) currStart.col else col

        skipWhitespace()

        return when (currType) {
            LET -> parseLetExpression(minCol)

            else -> parseElvis(minCol)
        }
    }

    // ==========================================================
    // parse-Methods that extract the next expression from the source
    // ==========================================================

    private fun parseElvis(col: Int): GValue? {
        val start = currStart

        val predicate = parseOperation(col)
        if (predicate != null && checkType(col, ELVIS) && currToken.content == "?") {
            nextToken()

            val thenExpr = parseOperation(col)
            assertNotNull(thenExpr)

            assertTypeWithContent(col, ELVIS, ":")
            nextToken()

            val elseExpr = parseOperation(col)
            assertNotNull(elseExpr)

            return GIfExpression(sourceName, start, currEnd, predicate, thenExpr!!, elseExpr!!)
        }

        return predicate
    }

    private fun parseOperation(col: Int): GValue? {
        val start = currStart

        val op1 = parseAddition(col)
        if (op1 != null && checkType(col, OP, OP_ID)) {
            val op = parseOperator(col)

            val op2 = parseProduct(col)
            assertNotNull(op2)


            return GOperation(sourceName, start, currEnd, op, op1, op2!!)
        }

        return op1
    }


    private fun parseAddition(col: Int): GValue? {
        val start = currStart

        val sum1 = parseProduct(col)

        if (sum1 != null && checkType(col, OP)) {
            if (currToken.content == "+" || currToken.content == "-") {
                val op = parseOperator(col)

                val sum2 = parseProduct(col)
                assertNotNull(sum2)

                return GOperation(sourceName, start, currEnd, op, sum1, sum2!!)
            }
        }

        return sum1
    }


    private fun parseProduct(col: Int): GValue? {
        val start = currStart

        val fac1 = parseFunctionCall(col)

        if (fac1 != null && checkType(col, OP)) {
            if (currToken.content == "*" || currToken.content == "/") {
                val op = parseOperator(col)

                val fac2 = parseFunctionCall(col)
                assertNotNull(fac2)

                return GOperation(sourceName, start, currEnd, op, fac1, fac2!!)
            }
        }

        return fac1
    }

    private fun parseFunctionCall(col: Int): GValue? {
        val start = currStart

        val elem1 = parseElement(col)

        // TODO parse rest of functioncall
        return elem1
    }

    private fun parseElement(col: Int): GValue? {
        if (currStart.col < col)
            return null

        return when (currType) {
            ID, FUNC_OP -> parseIdentifier(col)

            NUMBER -> parseNumber(col)

            STRING -> parseString(col)

            UNIT -> parseUnit(col)

            PROPERTY -> parseProperty(col)

            else -> throw createIllegalTokenException()
        }
    }


    private fun parseUnit(col: Int): GUnit {
        assertType(col, UNIT)
        return GUnit(sourceName, currStart, currEnd)
    }

    private fun parseString(col: Int): GValue {
        assertType(col, STRING)
        val content = currToken.content

        val ret = GString(sourceName, currStart, currEnd, content)
        nextToken()
        return ret
    }

    private fun parseNumber(col: Int): GValue {
        assertType(col, NUMBER)
        val content = currToken.content

        val ret = if (content.indexOf('.') >= 0) {
            GFloat(sourceName, currStart, currEnd, content.toDouble())
        } else {
            GInteger(sourceName, currStart, currEnd, content.toLong())
        }
        nextToken()
        return ret
    }

    private fun parseProperty(col: Int): GProperty {
        assertType(col, PROPERTY)
        val name = currToken.content

        val ret = GProperty(sourceName, currStart, currEnd, name)
        nextToken()
        return ret
    }


    private fun parseIdentifier(col: Int): GIdentifier {
        assertType(col, ID, FUNC_OP)
        val name = currToken.content

        val ret = GIdentifier(sourceName, currStart, currEnd, name, currType == FUNC_OP)
        nextToken()
        return ret
    }

    private fun parseOperator(col: Int): GOperator {
        assertType(col, OP, OP_ID)
        val name = currToken.content

        val ret = GOperator(sourceName, currStart, currEnd, name, currType == OP)
        nextToken()
        return ret
    }

    private fun parseLetExpression(col: Int): GLetExpression {
        val start = currStart
        nextToken()

        val id = parseIdentifier(col)

        assertTypeWithContent(col, OP, "=")
        nextToken()

        val nextCol = currToken.start.col
        if (nextCol <= col)
            throw createIllegalColumnException(col + 1)
        val expression = nextExpression(nextCol) ?: throw createIllegalTokenException()

        return GLetExpression(sourceName, start, currEnd, id, expression)
    }

    // ==========================================================
    // assertions
    // ==========================================================

    private fun checkType(col: Int, vararg type: TokenType) =
        currStart.col >= col && type.indexOf(currType) >= 0


    private fun assertType(col: Int, vararg type: TokenType) {
        if (currStart.col < col)
            throw createIllegalColumnException(col)
        if (type.indexOf(currType) < 0)
            throw createIllegalTokenException()
    }

    private fun assertNotNull(expr: GValue?) {
        if (expr == null)
            throw createIllegalEndOfExpression()
    }

    private fun assertTypeWithContent(col: Int, type: TokenType, content: String) {
        if (currStart.col < col)
            throw createIllegalColumnException(col)
        if (currType != type && currToken.content != content)
            throw createIllegalTokenException()

    }

    private fun createIllegalEndOfExpression() =
        EvaluationException(
            "Illegal end of expression",
            sourceName,
            currStart.line,
            currStart.col
        )

    private fun createIllegalColumnException(col: Int) =
        EvaluationException(
            "Illegal indentation of Token ${currToken.content} must be indented to column $col",
            sourceName,
            currStart.line,
            currStart.col
        )


    private fun createIllegalTokenException() =
        EvaluationException(
            "Illegal Token ${currToken.content}", sourceName, currStart.line, currStart.col
        )

    // ==========================================================
    // helper functions
    // ==========================================================


    private fun skipWhitespace() {
        while (currToken.type == EXEND) {
            nextToken()
        }
    }


    private fun nextToken() {
        currToken = lexer.nextToken()
        currType = currToken.type
        currStart = currToken.start
        currEnd = currToken.end
    }

}
