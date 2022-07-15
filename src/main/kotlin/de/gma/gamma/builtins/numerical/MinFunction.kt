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
        return if (float) {
            numList.map { it.toFloat() }.reduce { v1, v2 ->
                if (v1.doubleValue < v2.doubleValue) v1 else v2
            }
        } else {
            (numList as List<IntegerValue>).reduce { v1, v2 ->
                if (v1.longValue < v2.longValue) v1 else v2
            }
        }
    }
}
