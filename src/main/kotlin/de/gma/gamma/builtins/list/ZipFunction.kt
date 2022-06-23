package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.PairValue
import de.gma.gamma.datatypes.scope.Scope

class ZipFunction : BuiltinFunction(listOf("list-1", "list-2")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list1 = callParams[0].evaluate(scope).toList()
        val list2 = callParams[1].evaluate(scope).toList()

        val newList = list1.allItems().zip(list2.allItems()).map { (a, b) ->
            PairValue.build(a, b)
        }

        return ListValue.build(newList)
    }
}
