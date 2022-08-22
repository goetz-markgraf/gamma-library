package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

object SliceFunction : BuiltinFunction(listOf("from", "length", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val from = callParams[0].evaluate(scope).toInteger().longValue.toInt()
        val length = callParams[1].evaluate(scope).toInteger().longValue.toInt()
        val p = callParams[2].evaluate(scope)

        if (p is StringValue) return p.slice(from, length)

        val l = p.toList()

        return l.slice(from, length)
    }
}
