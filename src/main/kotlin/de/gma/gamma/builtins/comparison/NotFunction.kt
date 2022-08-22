package de.gma.gamma.builtins.comparison

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

object NotFunction : BuiltinFunction(listOf("a")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = callParams[0].evaluate(scope).toBoolean()

        return BooleanValue.build(!a.boolValue)
    }
}
