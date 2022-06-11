package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class FoldFunction : BuiltinFunction(listOf("initial", "function", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val initial = callParams[0].evaluate(scope)
        val function = callParams[1].evaluate(scope).toFunction()
        val list = callParams[2].evaluate(scope).toList()

        val result = list.allItems().fold(initial) { acc, item ->
            function.call(scope, listOf(acc, item))
        }

        return result
    }
}
