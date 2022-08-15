package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

object DoesNotContainPredicate : BuiltinFunction(listOf("predicate", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val item = callParams[0].evaluate(scope)
        val list = callParams[1].evaluate(scope).toList()

        return BooleanValue.build(!list.contains(item))
    }
}
