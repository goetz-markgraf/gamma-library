package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.PairValue

object FirstFunction : BuiltinFunction(listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val p = callParams[0].evaluate(scope)
        if (p is StringValue) return p.first()
        if (p is PairValue) return p.first()

        val l = p.toList()

        return l.first()
    }
}
