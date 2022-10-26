package de.gma.gamma.builtins.types

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.DataType
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue

class ToTypeFunction(private val type: DataType) : BuiltinFunction(listOf("obj")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val l = callParams[0].evaluate(scope)

        return when (type) {
            DataType.BOOLEAN -> l.toBoolean()
            DataType.LIST -> l.toList()
            DataType.PAIR -> l.toPair()
            DataType.FLOAT -> l.toFloat()
            DataType.INTEGER -> l.toInteger()
            DataType.STRING -> l.toStringValue()
            DataType.FUNCTION -> l.toFunction()
            DataType.RECORD -> l.toRecord(scope)
            DataType.PROPERTY -> l.toProperty()
            DataType.VOID -> if (l is VoidValue) l else throw createException("Cannot convert $this to ${type.name}")
            DataType.MODULE -> if (l is ModuleScope) l else throw createException("Cannot convert $this to ${type.name}")
        }
    }
}
