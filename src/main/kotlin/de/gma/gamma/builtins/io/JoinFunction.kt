package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Scope

object JoinFunction : BuiltinFunction(listOf("string", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val separator = callParams[0].evaluate(scope).toStringValue()
        val list = callParams[1].evaluate(scope).toList()

        val res = list.allItems().joinToString(separator.strValue) { it.evaluate(scope).toStringValue().strValue }

        return StringValue.build(res)
    }
}
