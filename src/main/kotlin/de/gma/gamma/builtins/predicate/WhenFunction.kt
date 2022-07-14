package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.checkForListOfPairs
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.EmptyValue

object WhenFunction : BuiltinFunction(listOf("cases")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        var list = callParams[0]

        if (list !is ListValue)
            list = list.evaluate(scope).toList()

        val cases = checkForListOfPairs(list)

        var result: Value = EmptyValue.build()

        cases.find {
            val item = it
            if (item.first().evaluate(scope).toBoolean().boolValue) {
                result = item.last().evaluate(scope)
                return@find true
            }
            return@find false
        }

        return result
    }
}
