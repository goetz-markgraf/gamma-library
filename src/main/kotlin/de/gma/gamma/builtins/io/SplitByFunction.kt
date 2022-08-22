package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

object SplitByFunction : BuiltinFunction(listOf("separator", "string")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val sep = callParams[0].evaluate(scope).toStringValue()
        val str = callParams[1].evaluate(scope).toStringValue()

        val split = str.strValue.split(sep.strValue)
        return ListValue.build(split.map {
            StringValue.build(it)
        })
    }
}
