package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class InsertFunction : BuiltinFunction(listOf("item", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val item = callParams[0].evaluate(scope)
        val l = callParams[1].evaluateToList(scope)

        return l.insert(item)
    }
}
