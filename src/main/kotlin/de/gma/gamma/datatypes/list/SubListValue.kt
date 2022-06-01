package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.parser.Position

class SubListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val origin: ListValue,
    val dropFirst: Int,
    val dropLast: Int
) : ListValue(sourceName, beginPos, endPos) {

    override fun allItems(): List<Value> =
        origin.allItems().dropLast(dropLast).drop(dropFirst)

    override fun first(): Value =
        origin.getAt(dropFirst)

    override fun last(): Value =
        origin.getAt(origin.size() - dropLast - 1)

    override fun getAt(pos: Int): Value =
        origin.getAt(pos + dropFirst)

    override fun size(): Int =
        origin.size() - dropLast - dropFirst

    override fun tail(): ListValue =
        newSublist(additionalDropFirst = 1)

    override fun dropLast(): ListValue =
        newSublist(additionalDropLast = 1)

    override fun slice(from: Int, length: Int): ListValue {
        val size = size()
        if (from > size || from < 0)
            return buildEmpty()

        val correctLength = if (from + length > size)
            size - from
        else length

        return newSublist(additionalDropFirst = from, additionalDropLast = size - from - correctLength)

    }

    override fun append(v: Value): ListValue =
        build(buildList {
            addAll(origin.allItems())
            add(v)
        })

    override fun insert(v: Value): ListValue =
        build(buildList {
            add(v)
            addAll(origin.allItems())
        })

    override fun join(v: ListValue): ListValue =
        build(buildList {
            addAll(v.allItems())
            addAll(origin.allItems())
        })


    override fun prettyPrint(): String =
        "<SubList>"

    private fun newSublist(additionalDropFirst: Int = 0, additionalDropLast: Int = 0) =
        SubListValue(
            builtInSource,
            nullPos,
            nullPos,
            origin,
            dropFirst + additionalDropFirst,
            dropLast + additionalDropLast
        )
}
