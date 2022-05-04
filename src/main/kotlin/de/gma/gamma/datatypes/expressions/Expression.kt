package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scoped.ScopedExpression
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

abstract class Expression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : Value(sourceName, beginPos, endPos) {
    override fun prepare(scope: Scope) =
        ScopedExpression(sourceName, beginPos, endPos, this, scope)
}
