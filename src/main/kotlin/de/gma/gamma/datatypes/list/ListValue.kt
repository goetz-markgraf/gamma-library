package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.Position

abstract class ListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : Value(sourceName, beginPos, endPos) {

    abstract fun allItems(): List<Value>

    abstract fun getAt(pos: Int): Value

    abstract fun size(): Int

    open fun first(): Value {
        return if (size() == 0)
            UnitValue.build()
        else
            getAt(0)
    }

    open fun last(): Value {
        val size = size()
        return if (size == 0)
            UnitValue.build()
        else
            getAt(size - 1)
    }

    open fun tail(): ListValue =
        slice(1, size() - 1)

    open fun dropLast(): ListValue =
        slice(0, size() - 1)

    abstract fun slice(from: Int, length: Int): ListValue

    abstract fun append(v: Value): ListValue

    abstract fun insert(v: Value): ListValue

    abstract fun join(v: ListValue): ListValue

    companion object {
        fun build(items: List<Value>) = SimpleList(builtInSource, nullPos, nullPos, items)
        fun buildEmpty() = build(emptyList())
    }

}
