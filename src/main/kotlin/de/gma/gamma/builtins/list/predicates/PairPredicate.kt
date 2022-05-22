package de.gma.gamma.builtins.list.predicates

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

class PairPredicate : BuiltinFunction(listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val l = callParams[0].evaluateToList(scope)

        return BooleanValue.build(l.size() == 2)
    }
}
