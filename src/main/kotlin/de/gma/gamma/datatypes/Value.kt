package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.AbstractFunction
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

    fun evaluateToBoolean(scope: Scope): BooleanValue =
        when (val value = evaluate(scope)) {
            is BooleanValue -> value
            is IntegerValue -> BooleanValue.build(value.intValue != 0L)
            is FloatValue -> BooleanValue.build(value.floatValue != 0.0)
            is StringValue -> BooleanValue.build(value.strValue.isNotEmpty())
            is UnitValue -> BooleanValue.build(false)
            else -> BooleanValue.build(true)
        }

    fun evaluateToList(scope: Scope): ListValue =
        when (val value = evaluate(scope)) {
            is ListValue -> value
            is UnitValue -> ListValue.build(emptyList())
            else -> ListValue.build(listOf(value))
        }

    fun evaluateToFloat(scope: Scope): FloatValue =
        when (val value = evaluate(scope)) {
            is FloatValue -> value
            is IntegerValue -> FloatValue.build(value.intValue.toDouble())
            else -> throw createException("$value cannot be converted to float")
        }

    fun evaluateToString(scope: Scope): StringValue =
        when (val value = evaluate(scope)) {
            is StringValue -> value
            else -> StringValue.build(value.prettyPrint())
        }

    fun evaluateToInteger(scope: Scope): IntegerValue =
        when (val value = evaluate(scope)) {
            is IntegerValue -> value
            is FloatValue -> IntegerValue.build(value.floatValue.toLong())
            else -> throw createException("$value is not an integer value")
        }

    fun evaluateToFunction(scope: Scope): AbstractFunction {
        val value = evaluate(scope)
        if (value is AbstractFunction) {
            return value
        } else {
            throw createException("$value is not a function")
        }
    }

    protected fun createException(message: String) =
        EvaluationException(message, sourceName, beginPos.line, beginPos.col)
}
