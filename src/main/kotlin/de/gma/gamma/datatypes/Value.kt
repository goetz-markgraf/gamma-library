package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.AbstractFunction
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

    fun evaluateToBoolean(scope: Scope): BooleanValue =
        when (val value = evaluate(scope)) {
            is BooleanValue -> value
            is IntegerValue -> BooleanValue.build(value.intValue != 0L)
            is FloatValue -> BooleanValue.build(value.floatValue != 0.0)
            is StringValue -> BooleanValue.build(value.strValue.isNotEmpty())
            is UnitValue -> BooleanValue.build(false)
            else -> BooleanValue.build(true)
        }

    fun evaluateToFunction(scope: Scope): AbstractFunction {
        val value = evaluate(scope)
        if (value is AbstractFunction) {
            return value
        } else {
            throw EvaluationException(
                "$value is not a function",
                value.sourceName,
                value.beginPos.line,
                value.beginPos.col
            )
        }
    }
}
