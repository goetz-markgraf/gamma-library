package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

abstract class FunctionTwoNumbersToBoolean() : BuiltinFunction(listOf("a", "b")) {

    protected fun operateOnTwoNumbers(
        scope: Scope,
        callParams: List<Value>,
        operationInteger: (Long, Long) -> Boolean,
        operationFloat: (Double, Double) -> Boolean
    ): Value? {
        val a = extractNumber(callParams[0].evaluate(scope))
        val b = extractNumber(callParams[1].evaluate(scope))

        if (a is IntegerValue && b is IntegerValue)
            return BooleanValue.build(operationInteger(a.longValue, b.longValue))

        if ((a is FloatValue || a is IntegerValue) && (b is FloatValue || b is IntegerValue)) {
            val af = a.toFloat()
            val bf = b.toFloat()

            return BooleanValue.build(operationFloat(af.doubleValue, bf.doubleValue))
        }

        return null
    }
}
