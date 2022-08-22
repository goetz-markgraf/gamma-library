package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.parser.Position

class SubList(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val origin: ListValue,
    val dropFirst: Int,
    val dropLast: Int
) : ListValue(sourceName, beginPos, endPos) {

    override fun allItems(): List<Value> =
        origin.allItems().subList(dropFirst, origin.size() - dropLast)

    override fun getAt(pos: Int): Value =
        origin.getAt(pos + dropFirst)

    override fun size(): Int =
        origin.size() - dropLast - dropFirst

    override fun slice(from: Int, length: Int): ListValue {
        val size = size()
        if (from > size || from < 0)
            return buildEmpty()

        val correctLength = if (from + length > size)
            size - from
        else length

        return buildSublist(
            origin,
            dropFirst = dropFirst + from,
            dropLast = dropLast + size - from - correctLength
        )

    }

    override fun append(v: Value): ListValue =
        build(buildList {
            addAll(allItems())
            add(v)
        })

    override fun insertFirst(v: Value): ListValue =
        build(buildList {
            add(v)
            addAll(allItems())
        })

    override fun appendAll(v: ListValue): ListValue =
        build(buildList {
            addAll(v.allItems())
            addAll(allItems())
        })


    companion object {
        fun buildSublist(origin: ListValue, dropFirst: Int, dropLast: Int) =
            SubList(
                builtInSource,
                nullPos,
                nullPos,
                origin,
                dropFirst,
                dropLast
            )
    }
}
