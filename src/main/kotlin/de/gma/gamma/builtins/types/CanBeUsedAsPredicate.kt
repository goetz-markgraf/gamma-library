package de.gma.gamma.builtins.types

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.DataType
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.datatypes.values.PropertyValue
import de.gma.gamma.datatypes.values.VoidValue

class CanBeUsedAsPredicate(private val type: DataType) : BuiltinFunction(listOf("obj")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val l = callParams[0].evaluate(scope)

        val ret = when (type) {
            DataType.BOOLEAN -> true
            DataType.LIST -> true
            DataType.PAIR -> checkConvert { l.toPair() }
            DataType.FLOAT -> checkConvert { l.toFloat() }
            DataType.INTEGER -> checkConvert { l.toInteger() }
            DataType.STRING -> true
            DataType.FUNCTION -> l is FunctionValue || l is PropertyValue
            DataType.RECORD -> checkConvert { l.toRecord(scope) }
            DataType.PROPERTY -> true
            DataType.VOID -> l is VoidValue
            DataType.MODULE -> l is ModuleScope
        }

        return BooleanValue.build(ret)
    }

    private fun checkConvert(func: () -> Unit): Boolean {
        try {
            func()
            return true
        } catch (e: java.lang.Exception) {
            return false
        }
    }
}
