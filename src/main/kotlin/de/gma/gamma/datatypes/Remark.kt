package de.gma.gamma.datatypes

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.parser.Position

class Remark(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String,
    private val documentation: Boolean = false
) : AbstractValue(sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (!documentation) "# $strValue" else "'${strValue.replace("'", "\\'")}'"

    override fun evaluate(scope: Scope) = VoidValue(sourceName, beginPos, endPos)

    companion object {
        fun buildDoc(doc: String) = Remark(builtInSource, nullPos, nullPos, doc, true)
    }

    override fun equals(other: Any?) =
        if (other !is Remark) false
        else other.strValue == strValue

    override fun hashCode() = strValue.hashCode()

}
