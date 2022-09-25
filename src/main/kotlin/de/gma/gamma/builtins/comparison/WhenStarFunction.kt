package de.gma.gamma.builtins.comparison

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.checkForListOfPairs
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue

object WhenStarFunction : BuiltinFunction(listOf("value", "cases")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val value = callParams[0]
        var list = callParams[1]

        if (list !is ListValue)
            list = list.evaluate(scope).toList()

        val cases = checkForListOfPairs(list)

        val pair = cases.find { case ->
            val predicate = case.first().evaluate(scope)
            (if (predicate is FunctionValue) {
                predicate.call(scope, listOf(value))
            } else {
                predicate
            }).toBoolean().boolValue
        }

        return pair?.last() ?: VoidValue.build()
    }
}
