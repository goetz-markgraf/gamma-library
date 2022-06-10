package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.FunctionTwoNumbersToBoolean
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException

class GreaterThanFunction : FunctionTwoNumbersToBoolean() {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val ret = operateOnTwoNumbers(
            scope,
            callParams,
            { i1, i2 -> i1 > i2 },
            { f1, f2 -> f1 > f2 }
        )

        if (ret != null)
            return ret
        else
            throw EvaluationException("> can only be called with two number values")
    }
}
