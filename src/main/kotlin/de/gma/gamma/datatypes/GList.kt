package de.gma.gamma.datatypes

import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class GList(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    items: List<GValue>
) : GValue(GValueType.LIST, sourceName, beginPos, endPos) {

    private var internalItems: List<GValue> = items

    override fun prettyPrint() = buildString {
        val complex = internalItems.indexOfFirst { it.type == GValueType.EXPRESSION } >= 0
        val splitChars = if (complex) "$CH_NEWLINE" else ", "

        append('{').append(if (complex) CH_NEWLINE else ' ')
        append(internalItems.joinToString(splitChars) { "${if (complex) "    " else ""}${it.prettyPrint()}" })
        append(if (complex) CH_NEWLINE else ' ')
        append('}')
    }

    override fun prepare(scope: Scope): GValue {
        internalItems = internalItems.map { it.prepare(scope) }

        return this
    }

    override fun evaluate(scope: Scope) = this

    // Functions to access the list's content
    fun size() = internalItems.size
}
