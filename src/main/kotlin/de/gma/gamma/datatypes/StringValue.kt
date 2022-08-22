package de.gma.gamma.datatypes

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class StringValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String
) : AbstractValue(sourceName, beginPos, endPos), Namespace {

    fun allItems(): List<StringValue> =
        strValue.toList().map { build(it.toString()) }

    fun first() =
        if (strValue.isEmpty())
            this
        else
            build(strValue.first().toString())

    fun last() =
        if (strValue.isEmpty())
            this
        else
            build(strValue.last().toString())

    fun getAt(pos: Int) =
        if (pos >= 0 && pos < size())
            build(strValue[pos].toString())
        else
            if (size() > 0)
                throw createException("Index out of bounds: $pos outside [0..${size() - 1}]")
            else
                throw createException("Index out of bounds: $pos outside empty string")

    fun size(): Int =
        strValue.length

    fun tail() =
        if (strValue.isEmpty()) this
        else build(strValue.drop(1))


    fun dropLast() =
        if (strValue.isEmpty())
            this
        else
            build(strValue.dropLast(1))

    fun slice(from: Int, length: Int): StringValue {
        val size = strValue.length
        if (from > size || from < 0)
            return build("")

        val correctLength = if (from + length > size)
            size - from
        else length

        return build(strValue.substring(from, from + correctLength))

    }

    override fun prettyPrint() = "\"$strValue\""

    override fun evaluate(scope: Scope) = this

    override fun prepare(scope: Scope) = this

    fun contains(item: Value) =
        item is StringValue && strValue.contains(item.strValue)

    override fun getValueForName(id: String, strict: Boolean): Value =
        when (id) {
            "first" -> first()
            "head" -> first()
            "last" -> last()
            "tail" -> tail()
            "size" -> IntegerValue.build(size().toLong())
            else -> if (strict) throw EvaluationException("property $id not found in $this") else VoidValue.build()
        }

    override fun containsNameLocally(id: String) =
        listOf(
            "first",
            "head",
            "last",
            "tail",
            "size"
        ).contains(id)


    override fun equals(other: Any?) =
        other is StringValue && other.strValue == strValue

    override fun hashCode() = strValue.hashCode()

    companion object {
        fun build(str: String) = StringValue(builtInSource, nullPos, nullPos, str)
    }
}
