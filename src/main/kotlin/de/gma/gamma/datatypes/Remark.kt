package de.gma.gamma.datatypes

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.Position

class Remark(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String,
    private val documentation: Boolean = false
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (!documentation) "# $strValue" else "'$strValue'"

    override fun evaluate(scope: Scope) = UnitValue(sourceName, beginPos, endPos)

    companion object {
        fun buildDoc(doc: String) = Remark(builtInSource, nullPos, nullPos, doc, true)
    }
}
