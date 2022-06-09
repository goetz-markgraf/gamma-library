package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

abstract class NumberOperationOnTwoNumbers() : BuiltinFunction(listOf("a", "b")) {

    protected fun operateOnTwoNumbers(
        scope: Scope,
        callParams: List<Value>,
        operationInteger: (Long, Long) -> Long,
        operationFloat: (Double, Double) -> Double
    ): Value? {
        val a = extractNumber(callParams[0].evaluate(scope))
        val b = extractNumber(callParams[1].evaluate(scope))

        if (a is IntegerValue && b is IntegerValue)
            return IntegerValue.build(operationInteger(a.intValue, b.intValue))

        if ((a is FloatValue || a is IntegerValue) && (b is FloatValue || b is IntegerValue)) {
            val af = a.toFloat()
            val bf = b.toFloat()

            return FloatValue.build(operationFloat(af.floatValue, bf.floatValue))
        }

        return null
    }
}
