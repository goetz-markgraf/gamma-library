package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GString(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String
) : GValue(GValueType.STRING, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "\"$strValue\""

}
