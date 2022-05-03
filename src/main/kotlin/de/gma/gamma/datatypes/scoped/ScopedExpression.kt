package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.expressions.GExpression
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class ScopedExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val expression: GExpression,
    private val lazyScope: Scope
) : GExpression(sourceName, beginPos, endPos) {

    private var value: GValue? = null

    override fun prettyPrint(): String {
        return if (value != null)
            value?.prettyPrint() ?: throw EvaluationException(
                "must not happen",
                sourceName,
                beginPos.line,
                beginPos.col
            )
        else
            "Lazy:${expression.prettyPrint()}"
    }

    override fun evaluate(scope: Scope): GValue {
        if (value == null) {
            value = expression.evaluate(lazyScope)
        }

        return value ?: throw EvaluationException("must not happen", sourceName, beginPos.line, beginPos.col)
    }
}
