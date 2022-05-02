package de.gma.gamma.datatypes.direct

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GInteger(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val intValue: Long
) : GValue(GValueType.INTEGER, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$intValue"

    override fun evaluate(scope: Scope) = this

}
