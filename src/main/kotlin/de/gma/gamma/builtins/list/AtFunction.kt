package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class AtFunction : BuiltinFunction(listOf("pos", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val pos = callParams[0].evaluateToInteger(scope)
        val l = callParams[1].evaluateToList(scope)

        return l.getAt(pos.intValue.toInt())
    }
}
