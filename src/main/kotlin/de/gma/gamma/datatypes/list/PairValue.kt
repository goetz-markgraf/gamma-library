package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.parser.Position

class PairValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    var left: Value,
    var right: Value
) : ListValue(sourceName, beginPos, endPos) {

    override fun size() = 2

    override fun getAt(pos: Int): Value =
        when (pos) {
            0 -> left
            1 -> right
            else -> throw createException("Index out of bounds: $pos outside [0..1]")
        }

    override fun slice(from: Int, length: Int): ListValue {
        if (from > 2 || from < 0)
            return buildEmpty()

        val correctLength = if (from + length > 2)
            2 - from
        else length

        return SubList.buildSublist(this, dropFirst = from, 2 - from - correctLength)
    }

    override fun allItems(): List<Value> =
        listOf(left, right)

    override fun append(v: Value): ListValue =
        build(buildList {
            add(left)
            add(right)
            add(v)
        })


    override fun insertFirst(v: Value): ListValue =
        build(buildList {
            add(v)
            add(left)
            add(right)
        })


    override fun appendAll(v: ListValue): ListValue =
        build(buildList {
            add(left)
            add(right)
            addAll(v.allItems())
        })


    override fun equals(other: Any?) =
        if (other !is ListValue) false
        else other.allItems() == allItems()

    override fun hashCode() = (left.hashCode() + right.hashCode())

    companion object {
        fun build(a: Value, b: Value) = PairValue(builtInSource, nullPos, nullPos, a, b)
    }
}
