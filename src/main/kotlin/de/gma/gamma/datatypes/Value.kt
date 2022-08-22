package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

interface Value {
    fun prettyPrint(): String

    fun evaluate(scope: Scope) = this

    fun prepare(scope: Scope) = this

    fun toBoolean(): BooleanValue

    fun toList(): ListValue

    fun toFloat(): FloatValue

    fun toStringValue(): StringValue

    fun toInteger(): IntegerValue

    fun toFunction(): FunctionValue

    fun toRecord(): RecordValue
}
