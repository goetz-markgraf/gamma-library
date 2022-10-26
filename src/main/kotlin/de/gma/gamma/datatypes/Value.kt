package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.datatypes.values.RecordValue

/**
 * These are the data types that are the result of an <code>evaluate()</code> call
 */
enum class DataType(val valueClass: Class<out Value>) {
    BOOLEAN(BooleanValue::class.java),
    LIST(ListValue::class.java),
    PAIR(PairValue::class.java),
    FLOAT(FloatValue::class.java),
    INTEGER(IntegerValue::class.java),
    STRING(StringValue::class.java),
    FUNCTION(FunctionValue::class.java),
    RECORD(RecordValue::class.java),
    PROPERTY(PropertyValue::class.java),
    VOID(VoidValue::class.java),
    MODULE(ModuleScope::class.java),
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
