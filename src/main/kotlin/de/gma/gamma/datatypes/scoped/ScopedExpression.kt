package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.expressions.Expression
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class ScopedExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val expression: Expression,
    private val lazyScope: Scope
) : Expression(sourceName, beginPos, endPos) {

    private var value: Value? = null

    override fun prettyPrint(): String {
        return if (value != null)
            value?.prettyPrint() ?: throw createException("must not happen")
        else
            "Lazy:${expression.prettyPrint()}"
    }

    override fun evaluate(scope: Scope): Value {
        if (value == null) {
            value = expression.evaluate(lazyScope)
        }

        return value ?: throw createException("must not happen")
    }
}
