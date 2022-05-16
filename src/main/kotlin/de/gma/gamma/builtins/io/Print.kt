package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class PrintFunction : BuiltinFunction(listOf("value")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val printVal = getParamEvaluated(callParams, 0, scope)

        println(printVal)

        return printVal
    }
}
