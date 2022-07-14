package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import java.lang.Math.abs

object AbsFunction : BuiltinFunction(listOf("value")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val num = extractNumber(callParams[0].evaluate(scope))

        if (num is FloatValue) {
            val ret = abs(num.doubleValue)
            return FloatValue.build(ret)
        } else {
            val ret = abs(num.toInteger().longValue)
            return IntegerValue.build(ret)
        }
    }
}
