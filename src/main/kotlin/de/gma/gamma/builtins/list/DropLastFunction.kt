package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class DropLastFunction : BuiltinFunction(listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val l = callParams[0].evaluate(scope).toList()

        return l.dropLast()
    }
}
