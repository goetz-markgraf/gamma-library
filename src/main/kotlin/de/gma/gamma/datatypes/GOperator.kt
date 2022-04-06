package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GOperator(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String,
    val operator: Boolean = true
) : GValue(GValueType.OPERATOR, sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (operator) identifier else "$identifier:"
}
