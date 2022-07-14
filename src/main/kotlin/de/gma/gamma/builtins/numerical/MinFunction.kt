package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

object MinFunction : BuiltinFunction(listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list = callParams[0].evaluate(scope).toList()

        val numList = list.allItems().map { extractNumber(it) }
        val float = numList.any { it is FloatValue }
        if (float) {
            val min = numList.fold(0.0) { acc, value ->
                val doubleValue = value.toFloat().doubleValue
                if (doubleValue < acc) doubleValue else acc
            }
            return FloatValue.build(min)
        } else {
            val min = numList.fold(0L) { acc, value ->
                val longValue = value.toInteger().longValue
                if (longValue < acc) longValue else acc
            }
            return IntegerValue.build(min)
        }
    }
}
