package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

object ConcatFunction : BuiltinFunction(listOf("list-1", "list-2")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list1 = callParams[0].evaluate(scope).toList()
        val list2 = callParams[1].evaluate(scope).toList()

        return if (list2.size() == 0)
            list1
        else if (list1.size() == 0)
            list2
        else
            return ListValue.build(
                list1.allItems() + list2.allItems()
            )
    }
}
