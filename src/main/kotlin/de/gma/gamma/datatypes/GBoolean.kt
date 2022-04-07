package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GBoolean(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val boolValue: Boolean
) : GValue(GValueType.STRING, sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (boolValue) "true" else "false"

}
