package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GRemark(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String,
    private val documentation: Boolean = false
) : GValue(GValueType.REMARK, sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (!documentation) "# $strValue" else "'$strValue'"

}
