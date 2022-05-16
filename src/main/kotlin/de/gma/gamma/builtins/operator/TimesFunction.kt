package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.parser.EvaluationException

class TimesFunction : BuiltinFunction(listOf("a", "b")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = getParamEvaluated(callParams, 0, scope)
        val b = getParamEvaluated(callParams, 1, scope)

        if (a is IntegerValue && b is IntegerValue)
            return IntegerValue.build(a.intValue * b.intValue)

        if ((a is FloatValue || a is IntegerValue) && (b is FloatValue || b is IntegerValue)) {
            val af = a.evaluateToFloat(scope)
            val bf = b.evaluateToFloat(scope)

            return FloatValue.build(af.floatValue * bf.floatValue)
        }

        throw EvaluationException("* can only be called with two number values", builtInSource, 0, 0)
    }
}
