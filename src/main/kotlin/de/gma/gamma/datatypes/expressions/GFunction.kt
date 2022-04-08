package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class GFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val params: List<GValue>,
    val expressions: List<GValue>
) : GValue(GValueType.FUNCTION, sourceName, beginPos, endPos) {

    override fun prettyPrint() = buildString {
        append("[ ")
        append(params.joinToString(" ") { it.prettyPrint() }).append(" ->")
        append(CH_NEWLINE)
        expressions.forEach {
            append("    ${it.prettyPrint()}").append(CH_NEWLINE)
        }
        append("]")
    }
}
