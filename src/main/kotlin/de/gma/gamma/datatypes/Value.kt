package de.gma.gamma.datatypes

import de.gma.gamma.builtins.namespaces.special.PropertyFunction
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

abstract class Value(
    val sourceName: String,
    val beginPos: Position,
    val endPos: Position
) {
    abstract fun prettyPrint(): String

    override fun toString() = prettyPrint()

    open fun evaluate(scope: Scope) = this

    open fun prepare(scope: Scope) = this

    fun toBoolean(): BooleanValue =
        when (this) {
            is BooleanValue -> this
            is IntegerValue -> BooleanValue.build(this.longValue != 0L)
            is FloatValue -> BooleanValue.build(this.doubleValue != 0.0)
            is StringValue -> BooleanValue.build(this.strValue.isNotEmpty())
            is UnitValue -> BooleanValue.build(false)
            else -> BooleanValue.build(true)
        }

    fun toList(): ListValue =
        when (this) {
            is ListValue -> this
            is UnitValue -> ListValue.build(emptyList())
            else -> ListValue.build(listOf(this))
        }

    fun toFloat(): FloatValue =
        when (this) {
            is FloatValue -> this
            is IntegerValue -> FloatValue.build(this.longValue.toDouble())
            is StringValue -> {
                val v = this.strValue.toDoubleOrNull()
                if (v == null) throw createException("$this is not an float value")
                else FloatValue.build(v)
            }

            else -> throw createException("$this cannot be converted to float")
        }

    fun toStringValue(): StringValue =
        when (this) {
            is StringValue -> this
            else -> StringValue.build(this.prettyPrint())
        }

    fun toInteger(): IntegerValue =
        when (this) {
            is IntegerValue -> this
            is FloatValue -> IntegerValue.build(this.doubleValue.toLong())
            is StringValue -> {
                val v = this.strValue.toLongOrNull()
                if (v == null) throw createException("$this is not an integer value")
                else IntegerValue.build(v)
            }
            else -> throw createException("$this is not an integer value")
        }

    fun toFunction(): FunctionValue =
        when (this) {
            is FunctionValue -> this
            is PropertyValue -> PropertyFunction(this)
            else -> throw createException("$this is not a function")
        }

    fun toRecord(): RecordValue =
        when (this) {
            is RecordValue -> this
            else -> throw createException("$this is not a record")
        }

    fun createException(message: String) =
        EvaluationException(message, sourceName, beginPos.line, beginPos.col)
}
