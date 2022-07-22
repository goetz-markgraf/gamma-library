package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.prettyPrintList
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.values.EmptyValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

abstract class ListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : AbstractValue(sourceName, beginPos, endPos), Namespace {

    override fun prettyPrint() = prettyPrintList(allItems())

    abstract fun allItems(): List<Value>

    abstract fun getAt(pos: Int): Value

    abstract fun size(): Int

    open fun first(): Value {
        return if (size() == 0)
            EmptyValue.build()
        else
            getAt(0)
    }

    open fun last(): Value {
        val size = size()
        return if (size == 0)
            EmptyValue.build()
        else
            getAt(size - 1)
    }

    open fun tail(): ListValue =
        slice(1, size() - 1)

    open fun dropLast(): ListValue =
        slice(0, size() - 1)

    abstract fun slice(from: Int, length: Int): ListValue

    abstract fun append(v: Value): ListValue

    abstract fun insertFirst(v: Value): ListValue

    abstract fun appendAll(v: ListValue): ListValue

    override fun getValue(id: String, strict: Boolean): Value =
        when (id) {
            "first" -> first()
            "head" -> first()
            "last" -> last()
            "tail" -> tail()
            "size" -> IntegerValue.build(size().toLong())
            else -> if (strict) throw EvaluationException("property $id not found in $this") else EmptyValue.build()
        }

    override fun containsLocally(id: String) =
        listOf(
            "first",
            "head",
            "last",
            "tail",
            "size"
        ).contains(id)

    open fun contains(item: Value) =
        allItems().contains(item)


    override fun equals(other: Any?): Boolean {
        if (other !is ListValue)
            return false

        return allItems().equals(other.allItems())
    }

    override fun hashCode(): Int {
        return allItems().hashCode()
    }

    open fun persist(): ListValue = this

    companion object {
        fun build(items: List<Value>) =
            if (items.size == 2)
                PairValue(builtInSource, nullPos, nullPos, items[0], items[1])
            else
                SimpleList(builtInSource, nullPos, nullPos, items)

        fun buildEmpty() = build(emptyList())
    }

}
