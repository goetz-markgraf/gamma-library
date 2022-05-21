package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException

class AppendFunction : BuiltinFunction(listOf("item", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val item = getParamEvaluated(callParams, 0, scope)
        val l = getParamEvaluated(callParams, 1, scope)

        if (l is ListValue)
            return l.append(item)

        throw EvaluationException("size can only be called a list", builtInSource, 0, 0)
    }
}
