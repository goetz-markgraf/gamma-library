package de.gma.gamma.datatypes.direct

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GString(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String
) : GValue(GValueType.STRING, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "\"$strValue\""

    override fun evaluate(scope: Scope) = this

}
