package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

class ToFunction : BuiltinFunction(listOf("a", "b")) {

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = callParams[0].evaluate(scope)
        val b = callParams[1].evaluate(scope)

        return ListValue.build(listOf(a, b))
    }
}
