package de.gma.gamma.datatypes.direct

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GRemark(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String,
    private val documentation: Boolean = false
) : GValue(GValueType.REMARK, sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (!documentation) "# $strValue" else "'$strValue'"

    override fun evaluate(scope: Scope) = GUnit(sourceName, beginPos, endPos)
}
