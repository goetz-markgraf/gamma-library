package de.gma.gamma.datatypes.direct

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GFloat(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val floatValue: Double
) : GValue(GValueType.FLOAT, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$floatValue"

    override fun evaluate(scope: Scope) = this

}
