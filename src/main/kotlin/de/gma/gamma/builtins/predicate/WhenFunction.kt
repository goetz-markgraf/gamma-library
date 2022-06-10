package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractListOfPairFromList
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue

class WhenFunction : BuiltinFunction(listOf("cases")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list = callParams[0].evaluate(scope).toList()
        val cases = extractListOfPairFromList(list, scope)

        var result: Value = UnitValue.build()

        cases.find {
            if (it.left.evaluate(scope).toBoolean().boolValue) {
                result = it.right.evaluate(scope)
                return@find true
            }
            return@find false
        }

        return result
    }
}
