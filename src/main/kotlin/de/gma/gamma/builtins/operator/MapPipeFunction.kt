package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.list.MapFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

object MapPipeFunction : BuiltinFunction(listOf("v", "f")) {

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = callParams[0].evaluate(scope)
        val b = callParams[1].evaluate(scope).toFunction()

        return MapFunction.callInternal(scope, listOf(b, a))
    }
}
