package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue

object FindFunction : BuiltinFunction(listOf("predicate", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val function = callParams[0].evaluate(scope).toFunction()
        val list = callParams[1].evaluate(scope).toList()

        val ret = list.allItems().find { item ->
            function.call(scope, listOf(item)).toBoolean().boolValue
        }

        return ret ?: VoidValue.build()
    }
}
