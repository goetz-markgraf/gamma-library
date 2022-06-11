package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class AppendAllFunction : BuiltinFunction(listOf("item", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val items = callParams[0].evaluate(scope).toList()
        val l = callParams[1].evaluate(scope).toList()

        return l.appendAll(items)
    }
}
