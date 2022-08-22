package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

object ContainsPredicate : BuiltinFunction(listOf("predicate", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val item = callParams[0].evaluate(scope)
        val p = callParams[1].evaluate(scope)

        if (p is StringValue) return BooleanValue.build(p.contains(item))

        val l = p.toList()

        return BooleanValue.build(l.contains(item))
    }
}
