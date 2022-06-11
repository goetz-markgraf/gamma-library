package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class IdentityFunction() : BuiltinFunction(
    listOf("it")
) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value =
        callParams[0].evaluate(scope).toInteger()
}
