package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.parser.EvaluationException

class GreaterThanOrEqualFunction : BuiltinFunction(listOf("a", "b")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val a = callParams[0].evaluate(scope)
        val b = callParams[1].evaluate(scope)

        if (a is IntegerValue && b is IntegerValue)
            return BooleanValue.build(a.intValue >= b.intValue)

        if ((a is FloatValue || a is IntegerValue) && (b is FloatValue || b is IntegerValue)) {
            val af = a.evaluate(scope).toFloat()
            val bf = b.evaluate(scope).toFloat()

            return BooleanValue.build(af.floatValue >= bf.floatValue)
        }

        throw EvaluationException(">= can only be called with two number values", builtInSource, 0, 0)
    }
}
