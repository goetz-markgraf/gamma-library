package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractNumber
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

object NegativeFunction : BuiltinFunction(listOf("a")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = extractNumber(callParams[0].evaluate(scope))

        return when (a) {
            is IntegerValue -> IntegerValue.build(-a.longValue)
            is FloatValue -> FloatValue.build(-a.doubleValue)
            else -> a
        }
    }
}
