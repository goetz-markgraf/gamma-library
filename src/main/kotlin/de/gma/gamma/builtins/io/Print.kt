package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class PrintFunction : BuiltinFunction(listOf("value")) {
    override fun callInternal(scope: Scope): Value {
        val printVal = getEvaluated(scope, "value")

        println(printVal)
        return printVal
    }
}
