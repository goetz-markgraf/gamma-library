package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListGenerator
import de.gma.gamma.datatypes.scope.Scope

class ListGeneratorFunction : BuiltinFunction(listOf("size", "function")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val size = callParams[0].evaluate(scope).toInteger()
        val function = callParams[1].evaluate(scope).toFunction()

        return ListGenerator.build(size.intValue.toInt(), function)
    }
}
