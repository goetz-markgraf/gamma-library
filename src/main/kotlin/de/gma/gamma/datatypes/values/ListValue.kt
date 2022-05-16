package de.gma.gamma.datatypes.values

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.expressions.Expression
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class ListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    items: List<Value>
) : Value(sourceName, beginPos, endPos) {

    private var internalItems: List<Value> = items

    override fun prettyPrint() = buildString {
        val complex = internalItems.indexOfFirst { it is Expression } >= 0
        val splitChars = if (complex) "$CH_NEWLINE" else ", "

        append('{').append(if (complex) CH_NEWLINE else ' ')
        append(internalItems.joinToString(splitChars) { "${if (complex) "    " else ""}${it.prettyPrint()}" })
        append(if (complex) CH_NEWLINE else ' ')
        append('}')
    }

    override fun prepare(scope: Scope): Value {
        internalItems = internalItems.map { it.prepare(scope) }

        return this
    }

    override fun evaluate(scope: Scope) = this

    // Functions to access the list's content
    fun size() = internalItems.size

    override fun equals(other: Any?) =
        if (other !is ListValue) false
        else other.internalItems == internalItems

    override fun hashCode() = internalItems.hashCode()

}
