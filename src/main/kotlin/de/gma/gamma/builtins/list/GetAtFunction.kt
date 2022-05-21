package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.parser.EvaluationException

class GetAtFunction : BuiltinFunction(listOf("pos", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val pos = getParamEvaluated(callParams, 0, scope)
        val l = getParamEvaluated(callParams, 1, scope)

        if (l is ListValue && pos is IntegerValue)
            return l.getAt(pos.intValue.toInt())

        throw EvaluationException("get-at can only be called on a list with an integer position", builtInSource, 0, 0)
    }
}
