package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

object PrintFunction : BuiltinFunction(listOf("value")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val printVal = callParams[0].evaluate(scope)

        println(printVal.toStringValue().strValue)

        return printVal
    }
}
