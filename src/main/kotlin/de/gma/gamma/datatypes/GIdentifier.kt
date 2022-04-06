package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GIdentifier(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String,
    val operator: Boolean = false
) : GValue(GValueType.IDENTIFIER, sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (operator) "($identifier)" else identifier
}
