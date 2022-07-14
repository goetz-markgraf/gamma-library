package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.FunctionTwoNumbersToNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException
import kotlin.math.pow

object PowerToFunction : FunctionTwoNumbersToNumber() {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val ret = operateOnTwoNumbers(
            scope,
            callParams,
            { i1, i2 -> i1.toDouble().pow(i2.toDouble()).toLong() },
            { f1, f2 -> f1.pow(f2) }
        )

        if (ret != null)
            return ret
        else
            throw EvaluationException("* can only be called with two number values")
    }
}
