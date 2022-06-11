package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

class NotEqualFunction : BuiltinFunction(listOf("a", "b")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = callParams[0].evaluate(scope)
        val b = callParams[1].evaluate(scope)

        return BooleanValue.build(!a.equals(b))
    }
}
