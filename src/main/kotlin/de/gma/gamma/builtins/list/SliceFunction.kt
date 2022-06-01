package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class SliceFunction : BuiltinFunction(listOf("from", "length", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val from = callParams[0].evaluateToInteger(scope).intValue.toInt()
        val length = callParams[1].evaluateToInteger(scope).intValue.toInt()
        val l = callParams[2].evaluateToList(scope)

        return l.slice(from, length)
    }
}
