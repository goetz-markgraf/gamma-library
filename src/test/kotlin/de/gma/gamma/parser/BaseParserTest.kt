package de.gma.gamma.parser

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope

open class BaseParserTest {
    lateinit var parser: Parser


    protected fun getExpression(source: String): Value? {
        parser = Parser(source)
        return parser.nextExpression(-1)
    }

    protected fun getExpressions(source: String): List<Value> {
        parser = Parser(source)
        return buildList {
            var expr = parser.nextExpression(-1)
            while (expr != null) {
                add(expr)
                expr = parser.nextExpression(-1)
            }
        }
    }

    protected fun execute(source: String): Value? {
        parser = Parser(source)
        val scope = ModuleScope("test")

        var expr = parser.nextExpression()
        var result: Value? = null
        while (expr != null) {
            result = expr.evaluate(scope)

            expr = parser.nextExpression()
        }
        return result
    }
}
