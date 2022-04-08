package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GProperty(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String
) : GValue(GValueType.PROPERTY, sourceName, beginPos, endPos) {

    override fun prettyPrint() = ":$identifier"
}
