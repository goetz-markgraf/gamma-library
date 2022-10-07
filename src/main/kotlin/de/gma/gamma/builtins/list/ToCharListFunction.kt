package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

object ToCharListFunction : BuiltinFunction(listOf("string")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val p = callParams[0].evaluate(scope).toStringValue()

        return ListValue.build(p.allItems())
    }
}
