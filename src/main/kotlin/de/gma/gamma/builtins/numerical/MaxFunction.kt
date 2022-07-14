package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

object MaxFunction : BuiltinFunction(listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list = callParams[0].evaluate(scope).toList()

        val numList = list.allItems().map { extractNumber(it) }
        val float = numList.any { it is FloatValue }
        if (float) {
            val max = numList.fold(Double.NEGATIVE_INFINITY) { acc, value ->
                val doubleValue = value.toFloat().doubleValue
                if (doubleValue > acc) doubleValue else acc
            }
            return FloatValue.build(max)
        } else {
            val max = numList.fold(Long.MIN_VALUE) { acc, value ->
                val longValue = value.toInteger().longValue
                if (longValue > acc) longValue else acc
            }
            return IntegerValue.build(max)
        }
    }
}
