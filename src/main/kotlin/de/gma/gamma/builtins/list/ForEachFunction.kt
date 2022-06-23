package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue

object ForEachFunction : BuiltinFunction(listOf("function", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val function = callParams[0].evaluate(scope).toFunction()
        val list = callParams[1].evaluate(scope).toList()

        list.allItems().forEach { item ->
            function.call(scope, listOf(item))
        }

        return UnitValue.build()
    }
}
