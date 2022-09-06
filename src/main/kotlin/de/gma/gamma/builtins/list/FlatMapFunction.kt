package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

object FlatMapFunction : BuiltinFunction(listOf("function", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val function = callParams[0].evaluate(scope).toFunction()
        val list = callParams[1].evaluate(scope).toList()

        val newList = list.allItems().flatMap { function.call(scope, listOf(it)).toList().allItems() }

        return ListValue.build(newList)
    }

}
