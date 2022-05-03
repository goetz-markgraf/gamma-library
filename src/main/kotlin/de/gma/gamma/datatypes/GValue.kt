package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.direct.*
import de.gma.gamma.datatypes.functions.AbstractFunction
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

enum class GValueType {
    EXPRESSION,
    INTEGER,
    FLOAT,
    STRING,
    IDENTIFIER,
    UNIT,
    PROPERTY,
    REMARK,
    FUNCTION,
    LIST
}

abstract class GValue(
    val type: GValueType,
    val sourceName: String,
    val beginPos: Position,
    val endPos: Position
) {
    abstract fun prettyPrint(): String

    override fun toString() = prettyPrint()

    open fun evaluate(scope: Scope) = this

    open fun prepare(scope: Scope) = this

    fun evaluateToBoolean(scope: Scope): GBoolean =
        when (val value = evaluate(scope)) {
            is GBoolean -> value
            is GInteger -> GBoolean.build(value.intValue != 0L)
            is GFloat -> GBoolean.build(value.floatValue != 0.0)
            is GString -> GBoolean.build(value.strValue.isNotEmpty())
            is GUnit -> GBoolean.build(false)
            else -> GBoolean.build(true)
        }

    fun evaluateToFunction(scope: Scope): AbstractFunction {
        val value = evaluate(scope)
        if (value.type == GValueType.FUNCTION) {
            return value as AbstractFunction
        } else {
            throw EvaluationException(
                "${value.type} is not a function",
                value.sourceName,
                value.beginPos.line,
                value.beginPos.col
            )
        }
    }
}
