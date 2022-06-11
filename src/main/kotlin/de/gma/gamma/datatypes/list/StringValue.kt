package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

private const val ERROR_STRING_ONLY_WITH_STRING = "string can only be joined with a string"

class StringValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String
) : ListValue(sourceName, beginPos, endPos) {
    override fun allItems(): List<Value> =
        strValue.toList().map { build(it.toString()) }

    override fun first(): Value =
        if (strValue.isEmpty())
            this
        else
            build(strValue.first().toString())

    override fun last(): Value =
        if (strValue.isEmpty())
            this
        else
            build(strValue.last().toString())

    override fun getAt(pos: Int): Value =
        if (pos >= 0 && pos < size())
            build(strValue[pos].toString())
        else
            if (size() > 0)
                throw createException("Index out of bounds: $pos outside [0..${size() - 1}]")
            else
                throw createException("Index out of bounds: $pos outside empty string")

    override fun size(): Int =
        strValue.length

    override fun tail(): ListValue =
        if (strValue.isEmpty())
            this
        else
            build(strValue.drop(1))


    override fun dropLast(): ListValue =
        if (strValue.isEmpty())
            this
        else
            build(strValue.dropLast(1))

    override fun slice(from: Int, length: Int): ListValue {
        val size = strValue.length
        if (from > size || from < 0)
            return build("")

        val correctLength = if (from + length > size)
            size - from
        else length

        return build(strValue.substring(from, from + correctLength))

    }


    override fun append(v: Value): ListValue =
        if (v is StringValue)
            build(strValue + v.strValue)
        else
            throw createException(ERROR_STRING_ONLY_WITH_STRING)

    override fun insertFirst(v: Value): ListValue =
        if (v is StringValue)
            build(v.strValue + strValue)
        else
            throw createException(ERROR_STRING_ONLY_WITH_STRING)


    override fun appendAll(v: ListValue): ListValue =
        if (v is StringValue)
            build(strValue + v.strValue)
        else
            throw createException(ERROR_STRING_ONLY_WITH_STRING)


    override fun prettyPrint() = "\"$strValue\""

    override fun evaluate(scope: Scope) = this

    override fun contains(item: Value) =
        if (item is StringValue)
            strValue.contains(item.strValue)
        else
            super.contains(item)

    override fun equals(other: Any?) =
        if (other !is StringValue) false
        else other.strValue == strValue

    override fun hashCode() = strValue.hashCode()

    companion object {
        fun build(str: String) = StringValue(builtInSource, nullPos, nullPos, str)
    }
}
