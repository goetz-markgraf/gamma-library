package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

object AppendAllFunction : BuiltinFunction("append-all", listOf("first-list", "second-list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val firstList = callParams[0].evaluate(scope).toList()
        val secondList = callParams[1].evaluate(scope).toList()

        return firstList.appendAll(secondList)
    }
}
