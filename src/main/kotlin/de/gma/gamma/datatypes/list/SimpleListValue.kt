package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.expressions.Expression
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class SimpleListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    items: List<Value>
) : ListValue(sourceName, beginPos, endPos) {

    protected var internalItems: Array<Value> = items.toTypedArray()

    override fun prettyPrint() = buildString {
        val complex = internalItems.indexOfFirst { it is Expression } >= 0
        val splitChars = if (complex) "$CH_NEWLINE" else ", "

        append('{').append(if (complex) CH_NEWLINE else ' ')
        append(internalItems.joinToString(splitChars) { "${if (complex) "    " else ""}${it.prettyPrint()}" })
        append(if (complex) CH_NEWLINE else ' ')
        append('}')
    }

    override fun prepare(scope: Scope): Value {
        internalItems = internalItems.toList().map { it.prepare(scope) }.toTypedArray()

        return this
    }

    override fun evaluate(scope: Scope) = this

    override fun first(): Value =
        if (internalItems.isNotEmpty())
            internalItems.first()
        else
            UnitValue.build()

    override fun last(): Value =
        if (internalItems.isNotEmpty())
            internalItems.last()
        else
            UnitValue.build()

    override fun getAt(pos: Int): Value =
        if (pos >= 0 && pos < size())
            internalItems[pos]
        else
            if (size() > 0)
                throw EvaluationException(
                    "Index out of bounds: $pos outside [0..${size() - 1}]",
                    sourceName,
                    beginPos.line,
                    beginPos.col
                )
            else throw EvaluationException(
                "Index out of bounds: $pos outside empty list",
                sourceName,
                beginPos.line,
                beginPos.col
            )

    override fun allItems(): List<Value> =
        internalItems.asList()

    override fun size() = internalItems.size

    override fun tail(): ListValue =
        if (size() > 0)
            newSublist(dropFirst = 1)
        else
            this

    override fun dropLast(): ListValue =
        if (size() > 0)
            newSublist(dropLast = 1)
        else
            this

    override fun append(v: Value): ListValue =
        build(buildList {
            addAll(internalItems)
            add(v)
        })


    override fun insert(v: Value): ListValue =
        build(buildList {
            add(v)
            addAll(internalItems)
        })


    override fun concat(v: ListValue): ListValue =
        build(buildList {
            addAll(internalItems)
            addAll(v.allItems())
        })


    override fun equals(other: Any?) =
        if (other !is ListValue) false
        else other.allItems() == allItems()

    override fun hashCode() = internalItems.hashCode()

    private fun newSublist(dropFirst: Int = 0, dropLast: Int = 0) =
        SubListValue(
            builtInSource,
            nullPos,
            nullPos,
            this,
            dropFirst,
            dropLast
        )

}
