package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.parser.Position

abstract class ListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : Value(sourceName, beginPos, endPos) {

    abstract fun allItems() : List<Value>

    abstract fun first() : Value

    abstract fun last() : Value

    abstract fun getAt(pos: Int): Value

    abstract fun size() : Int

    abstract fun tail() : ListValue

    abstract fun dropLast(): ListValue

    abstract fun append(v: Value) : ListValue

    abstract fun insert(v: Value): ListValue

    abstract fun concat(v: ListValue): ListValue

    companion object {
        fun build(items: List<Value>) = SimpleListValue(builtInSource, nullPos, nullPos, items)
    }

}
