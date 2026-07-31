package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.FunctionTwoNumbersToNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.GammaException

object DividedByFunction : FunctionTwoNumbersToNumber("/") {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val ret = operateOnTwoNumbers(
            scope,
            callParams,
            { i1, i2 ->
                if (i2 == 0L) throw createException("Division by zero")
                i1 / i2
            },
            { f1, f2 ->
                if (f2 == 0.0) throw createException("Division by zero")
                f1 / f2
            }
        )

        return ret ?: throw GammaException("/ can only be called with two number values")
    }
}
