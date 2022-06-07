package de.gma.gamma.datatypes.list

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class SimpleList(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    items: List<Value>
) : ListValue(sourceName, beginPos, endPos) {

    protected var internalItems = items

    override fun prepare(scope: Scope): Value {
        internalItems = internalItems.map { it.prepare(scope) }

        return this
    }

    override fun evaluate(scope: Scope) = this

    override fun size() = internalItems.size

    override fun getAt(pos: Int): Value =
        if (pos >= 0 && pos < size())
            internalItems[pos]
        else
            if (size() > 0)
                throw createException("Index out of bounds: $pos outside [0..${size() - 1}]")
            else
                throw createException("Index out of bounds: $pos outside empty list")

    override fun slice(from: Int, length: Int): ListValue {
        if (from > internalItems.size || from < 0)
            return buildEmpty()

        val correctLength = if (from + length > internalItems.size)
            internalItems.size - from
        else length

        return SubList.buildSublist(this, dropFirst = from, internalItems.size - from - correctLength)
    }

    override fun allItems(): List<Value> =
        internalItems

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


    override fun join(v: ListValue): ListValue =
        build(buildList {
            addAll(internalItems)
            addAll(v.allItems())
        })


    override fun equals(other: Any?) =
        if (other !is ListValue) false
        else other.allItems() == allItems()

    override fun hashCode() = internalItems.hashCode()
}
