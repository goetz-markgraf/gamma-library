package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

class NegativeFunction : BuiltinFunction(listOf("a")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = extractNumber(callParams[0].evaluate(scope))

        return when (a) {
            is IntegerValue -> IntegerValue.build(-a.longValue)
            is FloatValue -> FloatValue.build(-a.doubleValue)
            else -> a
        }
    }
}
