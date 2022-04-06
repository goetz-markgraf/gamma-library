package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GUnit(
    sourceName: String,
    beginPos: Position,
    endPos: Position
) : GValue(GValueType.UNIT, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "())"

}
