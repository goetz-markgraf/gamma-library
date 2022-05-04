package de.gma.gamma.parser

import de.gma.gamma.datatypes.Value

open class BaseParserTest {
    lateinit var parser: Parser


    protected fun getExpression(source: String): Value? {
        parser = Parser(source, "Script")
        return parser.nextExpression(-1)
    }

    protected fun getExpressions(source: String): List<Value> {
        parser = Parser(source, "Script")
        return buildList {
            var expr = parser.nextExpression(-1)
            while (expr != null) {
                add(expr)
                expr = parser.nextExpression(-1)
            }
        }
    }
}
