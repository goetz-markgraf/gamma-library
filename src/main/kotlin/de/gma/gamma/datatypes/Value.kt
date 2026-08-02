package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.*

/**
 * These are the data types that are the result of an <code>evaluate()</code> call
 */
enum class DataType {
    BOOLEAN,
    LIST,
    PAIR,
    FLOAT,
    INTEGER,
    STRING,
    FUNCTION,
    RECORD,
    PROPERTY,
    VOID,
    MODULE
}

interface Value {
    fun prettyPrint(): String

    /**
     * Eagerly evaluates this value in [scope] and returns the result.
     * Literals return `this`. Expressions (e.g. [LetExpression], [FunctionCall])
     * compute and return their result.
     */
    fun evaluate(scope: Scope): Value = this

    /**
     * Creates a lazily-evaluated wrapper that captures [scope] at this point,
     * deferring actual evaluation until the value is accessed.
     *
     * Used when passing arguments to functions or storing unevaluated sub-expressions,
     * so that each argument sees the scope at the call site, not the scope inside
     * the callee. Implemented via [ScopedValue] / [ScopedFunction].
     *
     * Defaults to returning `this` for fully-evaluated values (literals, records, etc.)
     * that carry no unevaluated sub-expressions.
     */
    fun prepare(scope: Scope): Value = this

    fun toBoolean(): BooleanValue

    fun toList(): ListValue

    fun toPair(): PairValue

    fun toFloat(): FloatValue

    fun toInteger(): IntegerValue

    fun toStringValue(): StringValue

    fun toProperty(): PropertyValue

    fun toFunction(): FunctionValue

    fun toRecord(scope: Scope): RecordValue
}
