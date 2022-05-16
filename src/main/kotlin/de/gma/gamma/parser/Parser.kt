package de.gma.gamma.parser

import de.gma.gamma.datatypes.GIdentifierType
import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.expressions.*
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.values.*
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
    fun nextExpression(col: Int): Value? {
        val minCol = if (col < 0) currStart.col else col

        if (currStart.col < minCol)
            return null

        skipWhitespace()

        return when (currType) {
            DOCUMENTATION -> parseDocumentation(minCol)

            REMARK -> parseRemark(minCol)

            LET -> parseLetExpression(minCol)

            SET -> parseSetExpression(minCol)

            else -> parseElvis(minCol)
        }
    }

    // ==========================================================
    // parse-Methods that extract the next expression from the source
    // ==========================================================

    private fun parseRemark(col: Int): Remark {
        assertType(col, REMARK)

        val content = currToken.content

        val ret = Remark(sourceName, currStart, currEnd, content)
        nextToken()

        return ret
    }

    private fun parseDocumentation(col: Int): Value {
        assertType(col, DOCUMENTATION)

        val content = currToken.content

        val ret = Remark(sourceName, currStart, currEnd, content, true)
        nextToken()
        return if (currType == LET) {
            parseLetExpression(col, ret)
        } else {
            ret
        }
    }

    private fun parseElvis(col: Int): Value? {
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

            return IfExpression(sourceName, start, currEnd, predicate, thenExpr!!, elseExpr!!)
        }

        return predicate
    }

    private fun parseOperation(col: Int): Value? {
        val start = currStart

        val op1 = parseAddition(col)
        if (op1 != null && checkType(col, OP, ID_AS_OP)) {
            val op = parseOperator(col)

            val op2 = parseProduct(col)
            assertNotNull(op2)


            return FunctionCall.fromOperation(sourceName, start, currEnd, op, op1, op2!!)
        }

        return op1
    }


    private fun parseAddition(col: Int): Value? {
        val start = currStart

        val sum1 = parseProduct(col)

        if (sum1 != null && checkType(col, OP) &&
            (currToken.content == "+" || currToken.content == "-")
        ) {
            val op = parseOperator(col)

            val sum2 = parseProduct(col)
            assertNotNull(sum2)

            return FunctionCall.fromOperation(sourceName, start, currEnd, op, sum1, sum2!!)
        }

        return sum1
    }


    private fun parseProduct(col: Int): Value? {
        val start = currStart

        val fac1 = parseFunctionCall(col)

        if (fac1 != null && checkType(col, OP) &&
            (currToken.content == "*" || currToken.content == "/")
        ) {
            val op = parseOperator(col)

            val fac2 = parseFunctionCall(col)
            assertNotNull(fac2)

            return FunctionCall.fromOperation(sourceName, start, currEnd, op, fac1, fac2!!)
        }

        return fac1
    }

    private fun parseFunctionCall(col: Int): Value? {
        val start = currStart

        val elem1 = parseElement(col)

        // are there more params?
        val params = mutableListOf<Value>()
        var nextElem = parseElement(col + 1)
        while (nextElem != null) {
            params.add(nextElem)

            nextElem = parseElement(col + 1)
        }

        // there have been no params
        if (params.isEmpty())
            return elem1

        return FunctionCall(sourceName, start, currEnd, elem1!!, params)
    }

    private fun parseElement(col: Int): Value? {
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

    private fun parseOpenParens(col: Int): Value? {
        return when (currToken.content) {
            "(" -> parseBlock(col)

            "{" -> parseList(col)

            "[" -> parseFunction(col)

            else -> null
        }
    }

    private fun parseFunction(col: Int): FunctionValue {
        assertTypeWithContent(col, OPEN_PARENS, "[")
        val start = currStart
        nextToken()

        if (currType != UNIT && currType != ID)
            throw createEmptyParamsException()

        val params: List<String> = if (currType == UNIT) {
            nextToken()
            emptyList()
        } else buildList {
            while (currType == ID) {
                add(parseIdentifier(col).name)
            }
        }

        assertTypeWithContent(col, OP, "->")
        nextToken()

        val expressions = parseIndentedExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, "]")
        nextToken()

        return FunctionValue(sourceName, start, currEnd, params, expressions)
    }

    private fun parseList(col: Int): ListValue {
        assertTypeWithContent(col, OPEN_PARENS, "{")
        val start = currStart
        nextToken()

        val expressions = parseIndentedExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, "}")
        nextToken()

        return ListValue(sourceName, start, currEnd, expressions)
    }

    private fun parseBlock(col: Int): Value {
        assertTypeWithContent(col, OPEN_PARENS, "(")
        val start = currStart
        nextToken()

        val expressions = parseIndentedExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, ")")
        nextToken()

        return when {
            expressions.isEmpty() -> UnitValue(sourceName, start, currEnd)

            expressions.size == 1 -> expressions.first()

            else -> Block(sourceName, start, currEnd, expressions)
        }
    }

    private fun parseBoolean(col: Int): BooleanValue {
        assertType(col, TRUE, FALSE)

        val ret = BooleanValue(sourceName, currStart, currEnd, currType == TRUE)
        nextToken()
        return ret
    }

    private fun parseUnit(col: Int): UnitValue {
        assertType(col, UNIT)
        val ret = UnitValue(sourceName, currStart, currEnd)
        nextToken()
        return ret
    }

    private fun parseString(col: Int): Value {
        assertType(col, STRING)
        val content = currToken.content

        val ret = StringValue(sourceName, currStart, currEnd, content)
        nextToken()
        return ret
    }

    private fun parseNumber(col: Int): Value {
        assertType(col, NUMBER)
        val content = currToken.content

        val ret = if (content.indexOf('.') >= 0) {
            FloatValue(sourceName, currStart, currEnd, content.toDouble())
        } else {
            IntegerValue(sourceName, currStart, currEnd, content.toLong())
        }
        nextToken()
        return ret
    }

    private fun parseProperty(col: Int): PropertyValue {
        assertType(col, PROPERTY)
        val name = currToken.content

        val ret = PropertyValue(sourceName, currStart, currEnd, name)
        nextToken()
        return ret
    }


    private fun parseIdentifier(col: Int): Identifier {
        assertType(col, ID, OP_AS_ID)
        val name = currToken.content
        val type = if (currType == ID) GIdentifierType.ID else GIdentifierType.OP_AS_ID

        val ret = Identifier(sourceName, currStart, currEnd, name, type)
        nextToken()
        return ret
    }

    private fun parseOperator(col: Int): Identifier {
        assertType(col, OP, ID_AS_OP)
        val name = currToken.content
        val type = if (currType == OP) GIdentifierType.OP else GIdentifierType.ID_AS_OP

        val ret = Identifier(sourceName, currStart, currEnd, name, type)
        nextToken()
        return ret
    }

    private fun parseLetExpression(col: Int, documentation: Remark? = null): LetExpression {
        val start = currStart
        nextToken()

        val id = parseIdentifier(col)

        return if (currType == OP && currToken.content == "=") {
            parseSimpleLetExpression(col, start, id, documentation)
        } else if (currType == ID || currType == UNIT) {
            parseFunctionLetExpression(col, start, id, documentation)
        } else {
            throw createIllegalTokenException("=")
        }
    }

    private fun parseFunctionLetExpression(
        col: Int,
        start: Position,
        id: Identifier,
        documentation: Remark?
    ): LetExpression {
        val identifiers = if (currType == UNIT) {
            nextToken()
            emptyList()
        } else
            buildList {
                while (currType == ID)
                    add(parseIdentifier(col))
            }.map { it.name }

        assertTypeWithContent(col, ID, "=")
        nextToken()

        val funStart = currStart

        val expressions = parseIndentedExpressions(col)

        val function = FunctionValue(sourceName, funStart, currEnd, identifiers, expressions)

        return LetExpression(sourceName, start, currEnd, id, function, documentation)
    }

    private fun parseSimpleLetExpression(
        col: Int,
        start: Position,
        id: Identifier,
        documentation: Remark?
    ): LetExpression {
        nextToken()

        val nextCol =
            if (currType == OPEN_PARENS && currToken.content == "[")
                col
            else {
                if (currToken.start.col <= col)
                    throw createIllegalColumnException(col + 1)

                currToken.start.col
            }

        val expression = nextExpression(nextCol) ?: throw createIllegalTokenException()

        return LetExpression(sourceName, start, currEnd, id, expression, documentation)
    }

    private fun parseSetExpression(col: Int, documentation: Remark? = null): SetExpression {
        val start = currStart
        nextToken()

        val id = parseIdentifier(col)
        if (id.name.last() != '!')
            throw EvaluationException(
                "Only ids with '!' at end of name can be mutated",
                sourceName,
                id.beginPos.line,
                id.beginPos.col
            )

        assertTypeWithContent(col, OP, "=")
        nextToken()

        val nextCol =
            if (currType == OPEN_PARENS && currToken.content == "[")
                col
            else {
                if (currToken.start.col <= col)
                    throw createIllegalColumnException(col + 1)

                currToken.start.col
            }

        val expression = nextExpression(nextCol) ?: throw createIllegalTokenException()

        return SetExpression(sourceName, start, currEnd, id, expression, documentation)
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

    private fun assertNotNull(expr: Value?) {
        if (expr == null)
            throw createIllegalEndOfExpression()
    }

    private fun assertTypeWithContent(col: Int, type: TokenType, content: String) {
        if (currStart.col < col)
            throw createIllegalColumnException(col)
        if (currType != type && currToken.content != content)
            throw createIllegalTokenException(content)

    }

    private fun createIllegalEndOfExpression() =
        EvaluationException(
            "Illegal end of expression",
            sourceName,
            currStart.line,
            currStart.col
        )

    private fun createEmptyParamsException() =
        EvaluationException(
            "Function must have at least one parameter or ()",
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

    private fun createIllegalTokenException(expected: String? = null) =
        EvaluationException(
            "Illegal Token ${currToken.content}${if (expected != null) " but was expecting $expected" else ""}",
            sourceName,
            currStart.line,
            currStart.col
        )

    // ==========================================================
    // helper functions
    // ==========================================================


    private fun parseIndentedExpressions(col: Int): List<Value> {
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
