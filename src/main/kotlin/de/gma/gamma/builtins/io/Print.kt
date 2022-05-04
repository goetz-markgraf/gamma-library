package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.Scope

class PrintFunction : BuiltinFunction(listOf("*v")) {
    override fun callInternal(scope: Scope): Value {
        val printVal = getEvaluated(scope, "*v")

        println(printVal)
        return printVal
    }
}
