package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope

class GetAtFunction : BuiltinFunction(listOf("pos", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val pos = callParams[0].evaluate(scope)
        val obj = callParams[1].evaluate(scope)

        if (obj is Namespace && pos is StringValue) {
            val posStr = pos.toStringValue()
            val namespace = obj as Namespace

            return namespace.getValue(posStr.strValue)
        }

        val posInt = pos.toInteger()
        val list = obj.toList()

        return list.getAt(posInt.longValue.toInt())

    }
}
