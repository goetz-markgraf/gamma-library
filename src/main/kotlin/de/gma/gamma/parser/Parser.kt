package de.gma.gamma.parser

import de.gma.gamma.datatypes.*
import de.gma.gamma.datatypes.expressions.GBlock
import de.gma.gamma.datatypes.expressions.GFunctionCall
import de.gma.gamma.datatypes.expressions.GIfExpression
import de.gma.gamma.datatypes.expressions.GLetExpression
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

        if (currStart.col < minCol)
            return null

        skipWhitespace()

        return when (currType) {
            DOCUMENTATION -> parseDocumentation(minCol)

            REMARK -> parseRemark(minCol)

            LET -> parseLetExpression(minCol)

            else -> parseElvis(minCol)
        }
    }

    // ==========================================================
    // parse-Methods that extract the next expression from the source
    // ==========================================================

    private fun parseRemark(col: Int): GRemark {
        assertType(col, REMARK)

        val content = currToken.content

        val ret = GRemark(sourceName, currStart, currEnd, content)
        nextToken()

        return ret
    }

    private fun parseDocumentation(col: Int): GValue {
        assertType(col, DOCUMENTATION)

        val content = currToken.content

        val ret = GRemark(sourceName, currStart, currEnd, content, true)
        nextToken()
        return if (currType == LET) {
            parseLetExpression(col, ret)
        } else {
            ret
        }
    }

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
        if (op1 != null && checkType(col, OP, ID_AS_OP)) {
            val op = parseOperator(col)

            val op2 = parseProduct(col)
            assertNotNull(op2)


            return GFunctionCall.fromOperation(sourceName, start, currEnd, op, op1, op2!!)
        }

        return op1
    }


    private fun parseAddition(col: Int): GValue? {
        val start = currStart

        val sum1 = parseProduct(col)

        if (sum1 != null && checkType(col, OP) &&
            (currToken.content == "+" || currToken.content == "-")
        ) {
            val op = parseOperator(col)

            val sum2 = parseProduct(col)
            assertNotNull(sum2)

            return GFunctionCall.fromOperation(sourceName, start, currEnd, op, sum1, sum2!!)
        }

        return sum1
    }


    private fun parseProduct(col: Int): GValue? {
        val start = currStart

        val fac1 = parseFunctionCall(col)

        if (fac1 != null && checkType(col, OP) &&
            (currToken.content == "*" || currToken.content == "/")
        ) {
            val op = parseOperator(col)

            val fac2 = parseFunctionCall(col)
            assertNotNull(fac2)

            return GFunctionCall.fromOperation(sourceName, start, currEnd, op, fac1, fac2!!)
        }

        return fac1
    }

    private fun parseFunctionCall(col: Int): GValue? {
        val start = currStart

        val elem1 = parseElement(col)

        // are there more params?
        val params = mutableListOf<GValue>()
        var nextElem = parseElement(col + 1)
        while (nextElem != null) {
            params.add(nextElem)

            nextElem = parseElement(col + 1)
        }

        // there have been no params
        if (params.isEmpty())
            return elem1

        return GFunctionCall(sourceName, start, currEnd, elem1!!, params)
    }

    private fun parseElement(col: Int): GValue? {
        if (currStart.col < col)
            return null

        val endTokens = listOf(EOF, EXEND, CLOSE_PARENS, OP, ID_AS_OP, ERROR, LET, SET, TYPE, MODULE, ELVIS)
        if (endTokens.indexOf(currType) >= 0)
            return null

        return when (currType) {
            ID, OP_AS_ID -> parseIdentifier(col)

            NUMBER -> parseNumber(col)

            STRING -> parseString(col)

            UNIT -> parseUnit(col)

            PROPERTY -> parseProperty(col)

            TRUE, FALSE -> parseBoolean(col)

            OPEN_PARENS -> parseOpenParens(col)

            else -> throw createIllegalTokenException()
        }
    }

    private fun parseOpenParens(col: Int): GValue? {
        return when (currToken.content) {
            "(" -> parseBlock(col)

            "{" -> parseList(col)

            else -> null
        }
    }

    private fun parseList(col: Int): GList {
        assertTypeWithContent(col, OPEN_PARENS, "{")
        val start = currStart
        nextToken()

        val expressions = findExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, ")")
        nextToken()

        return GList(sourceName, start, currEnd, expressions)
    }

    private fun findExpressions(col: Int): List<GValue> {
        val newCol = currStart.col
        if (newCol <= col)
            throw createIllegalColumnException(col + 1)

        val expressions = buildList {
            var expr = nextExpression(newCol)
            while (expr != null) {
                add(expr)
                expr = nextExpression(newCol)
            }
        }
        return expressions
    }

    private fun parseBlock(col: Int): GValue {
        assertTypeWithContent(col, OPEN_PARENS, "(")
        val start = currStart
        nextToken()

        val expressions = findExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, ")")
        nextToken()

        return when {
            expressions.isEmpty() -> GUnit(sourceName, start, currEnd)

            expressions.size == 1 -> expressions.first()

            else -> GBlock(sourceName, start, currEnd, expressions)
        }
    }

    private fun parseBoolean(col: Int): GBoolean {
        assertType(col, TRUE, FALSE)

        val ret = GBoolean(sourceName, currStart, currEnd, currType == TRUE)
        nextToken()
        return ret
    }

    private fun parseUnit(col: Int): GUnit {
        assertType(col, UNIT)
        val ret = GUnit(sourceName, currStart, currEnd)
        nextToken()
        return ret
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
        assertType(col, ID, OP_AS_ID)
        val name = currToken.content
        val type = if (currType == ID) GIdentifierType.ID else GIdentifierType.OP_AS_ID

        val ret = GIdentifier(sourceName, currStart, currEnd, name, type)
        nextToken()
        return ret
    }

    private fun parseOperator(col: Int): GIdentifier {
        assertType(col, OP, ID_AS_OP)
        val name = currToken.content
        val type = if (currType == OP) GIdentifierType.OP else GIdentifierType.ID_AS_OP

        val ret = GIdentifier(sourceName, currStart, currEnd, name, type)
        nextToken()
        return ret
    }

    private fun parseLetExpression(col: Int, documentation: GRemark? = null): GLetExpression {
        val start = currStart
        nextToken()

        val id = parseIdentifier(col)

        assertTypeWithContent(col, OP, "=")
        nextToken()

        val nextCol = currToken.start.col
        if (nextCol <= col)
            throw createIllegalColumnException(col + 1)
        val expression = nextExpression(nextCol) ?: throw createIllegalTokenException()

        return GLetExpression(sourceName, start, currEnd, id, expression, documentation)
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
