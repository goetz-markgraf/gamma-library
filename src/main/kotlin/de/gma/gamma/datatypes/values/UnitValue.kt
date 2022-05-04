package de.gma.gamma.datatypes.values

import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class UnitValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "()"

    override fun evaluate(scope: Scope) = this
}
