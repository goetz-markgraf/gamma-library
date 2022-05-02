package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.GFunction
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

    fun evaluateToFunction(scope: Scope): GFunction {
        val value = evaluate(scope)
        if (value.type == GValueType.FUNCTION) {
            return value as GFunction
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
