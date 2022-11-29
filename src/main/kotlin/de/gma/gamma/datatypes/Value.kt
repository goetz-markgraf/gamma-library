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

    fun evaluate(scope: Scope) = this

    fun prepare(scope: Scope) = this

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
