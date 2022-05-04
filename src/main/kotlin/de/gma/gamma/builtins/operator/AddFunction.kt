package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException

class AddFunction : BuiltinFunction(listOf("*a", "*b")) {
    override fun callInternal(scope: Scope): Value {
        val a = getEvaluated(scope, "*a")
        val b = getEvaluated(scope, "*b")

        if (a is IntegerValue && b is IntegerValue)
            return IntegerValue.build(a.intValue + b.intValue)

        throw EvaluationException("+ can only be called with two integer values", builtInSource, 0, 0)
    }
}
