package de.gma.gamma.datatypes.direct

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GUnit(
    sourceName: String,
    beginPos: Position,
    endPos: Position
) : GValue(GValueType.UNIT, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "()"

    override fun evaluate(scope: Scope) = this
}
