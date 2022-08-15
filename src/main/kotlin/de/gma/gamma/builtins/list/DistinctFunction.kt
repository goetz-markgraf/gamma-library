package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

object DistinctFunction : BuiltinFunction(listOf("list-1")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list1 = callParams[0].evaluate(scope).toList()

        return ListValue.build(list1.allItems().distinct())
    }
}
