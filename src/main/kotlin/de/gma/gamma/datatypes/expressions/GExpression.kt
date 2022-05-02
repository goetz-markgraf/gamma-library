package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.scoped.ScopedExpression
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

abstract class GExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : GValue(GValueType.EXPRESSION, sourceName, beginPos, endPos) {
    override fun prepare(scope: Scope) =
        ScopedExpression(sourceName, beginPos, endPos, this, scope)
}
