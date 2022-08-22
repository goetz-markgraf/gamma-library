package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope

object GetPropertiesFunction : BuiltinFunction(listOf("record")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val namespace = callParams[0].evaluate(scope).toRecord()

        val keys = namespace.getPropertyNames().map { StringValue.build(it) }

        return ListValue.build(keys)
    }
}
