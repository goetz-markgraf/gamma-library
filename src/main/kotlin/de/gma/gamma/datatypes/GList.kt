package de.gma.gamma.datatypes

import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class GList(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val items: List<GValue>
) : GValue(GValueType.UNIT, sourceName, beginPos, endPos) {

    override fun prettyPrint() = buildString {
        val complex = items.indexOfFirst { it.type == GValueType.EXPRESSION } >= 0
        val splitChars = if (complex) "$CH_NEWLINE" else ", "

        append('{').append(if (complex) CH_NEWLINE else ' ')
        append(items.joinToString(splitChars) { "${if (complex) "    " else ""}${it.prettyPrint()}" })
        append(if (complex) CH_NEWLINE else ' ')
        append('}')
    }

}
