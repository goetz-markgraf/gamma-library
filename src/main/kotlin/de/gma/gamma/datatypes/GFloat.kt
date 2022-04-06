package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GFloat(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val floatValue: Double
) : GValue(GValueType.FLOAT, sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$floatValue"

}
