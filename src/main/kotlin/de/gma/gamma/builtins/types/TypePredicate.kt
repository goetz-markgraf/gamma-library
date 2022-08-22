package de.gma.gamma.builtins.types

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.DataType
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

class TypePredicate(private val type: DataType) : BuiltinFunction(listOf("obj")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val l = callParams[0].evaluate(scope)

        val valueClass = type.valueClass

        return BooleanValue.build(valueClass.isAssignableFrom(l.javaClass))
    }
}
