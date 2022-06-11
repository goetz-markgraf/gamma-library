package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Scope

class SplitFunction : BuiltinFunction(listOf("string")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val str = callParams[0].evaluate(scope).toStringValue()

        val split = str.strValue.split(' ')
        return ListValue.build(split.mapNotNull {
            if (it.isNotEmpty())
                StringValue.build(it)
            else null
        })
    }
}
