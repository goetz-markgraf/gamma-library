package de.gma.gamma.parser

import de.gma.gamma.builtins.io.JoinByFunction
import de.gma.gamma.datatypes.*
import de.gma.gamma.datatypes.expressions.*
import de.gma.gamma.datatypes.functions.LambdaFunction
import de.gma.gamma.datatypes.list.ListLiteral
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.parser.TokenType.*


class Parser(
    source: String,
    private val sourceName: String = "Script"
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
    fun nextExpression(col: Int = -1): Value? {
        val minCol = if (col < 0) currStart.col else col

        if (currStart.col < minCol)
            return null

        skipWhitespace()

        val ret = when (currType) {
            EOF -> return null

            DOCUMENTATION -> parseDocumentation(minCol)

            LET -> parseLetExpression(minCol)

            SET -> parseSetExpression(minCol)

            else -> parseTernary(minCol)
        }
        if (ret != null)
            return ret
        else if (col >= 0)
            return null

        throw EvaluationException(
            "Parsing error, cannot interpret current token ${currToken.content}",
            sourceName,
            currStart.line,
            currStart.col
        )
    }

    // ==========================================================
    // parse-Methods that extract the next expression from the source
    // ==========================================================

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

    private fun parseTernary(col: Int): Value? {
        val start = currStart

        val predicate = parseOperation(col, MAX_OPERATOR_LEVEL)
        if (predicate != null && checkType(col, TERNARY) && currToken.content == "?") {
            nextToken()

            val thenExpr = nextExpression(col)
            assertNotNull(thenExpr)

            assertTypeWithContent(col, TERNARY, ":")
            nextToken()

            val elseExpr = nextExpression(col)
            assertNotNull(elseExpr)

            return IfExpression(sourceName, start, currEnd, predicate, thenExpr!!, elseExpr!!)
        }

        return predicate
    }

    private fun parseOperation(col: Int, level: Int): Value? {
        val start = currStart

        var op1 =
            if (level == 0)
                parseFunctionCall(col)
            else
                parseOperation(col, level - 1)

        while (op1 != null && checkType(col, OP) && isOperatorInLevel(currToken.content, level)) {
            val op = parseOperator(col)

            val op2 =
                if (level == 0)
                    parseFunctionCall(col)
                else
                    parseOperation(col, level - 1)

            assertNotNull(op2)

            if ((op.name == "->" || op.name == "→") && op2 != null)
                op1 = PairValue(sourceName, start, currEnd, op1, op2)
            else
                op1 = OperaterCall(sourceName, start, currEnd, op, op1, op2!!, level)
        }

        return op1
    }


    private fun parseFunctionCall(col: Int): Value? {
        val start = currStart

        val elem1 = parseElement(col)

        val params = mutableListOf<Value>()

        // are there more params?
        var nextElem: Value? = null
        if (currStart.col > col)
            nextElem = parseElement(col)
        while (nextElem != null) {
            params.add(nextElem)

            if (currStart.col > col)
                nextElem = parseElement(col)
            else
                nextElem = null
        }

        if (params.isEmpty())
            return elem1

        return FunctionCall(sourceName, start, currEnd, elem1!!, params)
    }

    private fun parseElement(col: Int): Value? {
        if (currStart.col < col)
            return null

        val endTokens =
            listOf(EOF, EXEND, CLOSE_PARENS, OP, ERROR, LET, SET, TYPE, MODULE, TERNARY, DOCUMENTATION)
        if (endTokens.indexOf(currType) >= 0)
            return null

        return when (currType) {
            ID, OP_AS_ID -> parseCompoundIdentifier(col)

            NUMBER -> parseNumber(col)

            STRING -> parseString(col)

            STRING_INTERPOLATION -> parseStringInterpolation(col)

            VOID -> parseVoid(col)

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

    private fun parseFunction(col: Int): LambdaFunction {
        assertTypeWithContent(col, OPEN_PARENS, "[")
        val start = currStart
        nextToken()

        if (currType != VOID && currType != ID)
            throw createEmptyParamsException()

        val params: List<String> = if (currType == VOID) {
            nextToken()
            emptyList()
        } else buildList {
            while (currType == ID) {
                add(parseIdentifier(col).name)
            }
        }

        assertTypeWithContent(col, TERNARY, ":")
        nextToken()

        val expressions = parseIndentedExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, "]")
        nextToken()

        return LambdaFunction(sourceName, start, currEnd, params, expressions)
    }

    private fun parseList(col: Int): ListValue {
        assertTypeWithContent(col, OPEN_PARENS, "{")
        val start = currStart
        nextToken()

        val expressions = parseIndentedExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, "}")
        nextToken()

        return ListLiteral(sourceName, start, currEnd, expressions)
    }

    private fun parseBlock(col: Int, withinString: Boolean = false): Value {
        assertTypeWithContent(col, OPEN_PARENS, "(")
        val start = currStart
        nextToken()

        val expressions = parseIndentedExpressions(col)

        assertTypeWithContent(col, CLOSE_PARENS, ")")
        nextToken(withinString)

        return when {
            expressions.isEmpty() -> VoidValue(sourceName, start, currEnd)

            // TODO kann das raus?
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

    private fun parseVoid(col: Int): VoidValue {
        assertType(col, VOID)
        val ret = VoidValue(sourceName, currStart, currEnd)
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

    private fun parseStringInterpolation(col: Int): Value {
        assertType(col, STRING_INTERPOLATION)
        val start = currStart
        val content = buildList {
            add(StringValue(sourceName, currStart, currEnd, currToken.content))
            nextToken()
            var addExpr = true
            while (addExpr) {
                add(parseBlock(col, true))
                assertType(col, STRING, STRING_INTERPOLATION)
                if (currType == STRING)
                    addExpr = false
                if (currToken.content.isNotEmpty())
                    add(StringValue(sourceName, currStart, currEnd, currToken.content))
                nextToken()
            }
        }

        val ret = FunctionCall(
            sourceName,
            start,
            currEnd,
            JoinByFunction,
            listOf<Value>(StringValue.build(""), ListValue.build(content))
        )
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

    private fun parseCompoundIdentifier(col: Int): Value {
        val start = currStart
        if (currType == OP_AS_ID)
            return parseIdentifier(col)

        val ids = buildList {
            add(parseIdentifier(col))
            while (currStart.col >= col && currType == OP && currToken.content == ".") {
                nextToken()
                add(parseIdentifier(col))
            }
        }

        if (ids.size == 1)
            return ids.first()
        else
            return CompoundIdentifier(sourceName, start, currEnd, ids.map { it.name })
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
        assertType(col, OP)
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
        } else if (currType == ID || currType == VOID) {
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
        val identifiers = if (currType == VOID) {
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

        val function = LambdaFunction(sourceName, funStart, currEnd, identifiers, expressions)

        return LetExpression(sourceName, start, currEnd, id, function, documentation)
    }

    private fun parseSimpleLetExpression(
        col: Int,
        start: Position,
        id: Identifier,
        documentation: Remark?
    ): LetExpression {
        nextToken()

        if (currToken.start.col <= col)
            throw createIllegalColumnException(col + 1)

        val expression = nextExpression(col) ?: throw createIllegalTokenException()

        return LetExpression(sourceName, start, currEnd, id, expression, documentation)
    }

    private fun parseSetExpression(col: Int, documentation: Remark? = null): SetExpression {
        val start = currStart
        nextToken()

        val id = parseIdentifier(col)
        if (id.name.last() != '!')
            throw EvaluationException(
                "Only ids with '!' at end of name can be mutated",
                sourceName, start.line, start.col
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

    private fun assertTypeWithContent(col: Int, type: TokenType, vararg content: String) {
        if (currStart.col < col)
            throw createIllegalColumnException(col)
        if (currType != type && content.indexOf(currToken.content) < 0)
            throw createIllegalTokenException(content.toList().toString())

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
            "Illegal Token '$currToken'${if (expected != null) " but was expecting $expected" else ""}",
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


    private fun nextToken(withinString: Boolean = false) {
        currToken = if (withinString)
            lexer.parseString(false)
        else
            lexer.nextToken()
        currType = currToken.type
        currStart = currToken.start
        currEnd = currToken.end
    }

}
