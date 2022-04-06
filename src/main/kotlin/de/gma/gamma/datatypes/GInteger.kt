package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GInteger(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val intValue: Long
) : GValue(GValueType.INTEGER, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$intValue"
}
