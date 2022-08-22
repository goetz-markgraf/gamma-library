package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue

object GetAtStarFunction : BuiltinFunction(listOf("pos", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val pos = callParams[0].evaluate(scope)
        val obj = callParams[1].evaluate(scope)

        if (obj is Namespace && pos is StringValue) {
            val posStr = pos.toStringValue()
            val namespace = obj as Namespace

            return namespace.getValueForName(posStr.strValue, strict = false)
        }

        val posInt = pos.toInteger().longValue.toInt()
        val list = obj.toList()

        return if (posInt >= 0 && posInt < list.size())
            list.getAt(posInt)
        else
            VoidValue.build()

    }
}
