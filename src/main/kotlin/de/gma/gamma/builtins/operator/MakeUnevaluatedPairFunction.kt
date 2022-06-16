package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

class MakeUnevaluatedPairFunction : BuiltinFunction(listOf("a", "b")) {

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = callParams[0]
        val b = callParams[1]

        return ListValue.build(listOf(a, b))
    }
}
