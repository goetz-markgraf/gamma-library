package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.IdentityFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListGenerator
import de.gma.gamma.datatypes.scope.Scope

object RepeatFunction : BuiltinFunction(listOf("size")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val size = callParams[0].evaluate(scope).toInteger()

        return ListGenerator.build(size.longValue.toInt(), IdentityFunction())
    }

}
