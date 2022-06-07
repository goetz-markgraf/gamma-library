package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.UnitValue
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
            is IntegerValue -> BooleanValue.build(this.intValue != 0L)
            is FloatValue -> BooleanValue.build(this.floatValue != 0.0)
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
            is IntegerValue -> FloatValue.build(this.intValue.toDouble())
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
            is FloatValue -> IntegerValue.build(this.floatValue.toLong())
            else -> throw createException("$this is not an integer value")
        }

    fun toFunction(): FunctionValue {
        if (this is FunctionValue) {
            return this
        } else {
            throw createException("$this is not a function")
        }
    }

    protected fun createException(message: String) =
        EvaluationException(message, sourceName, beginPos.line, beginPos.col)
}
