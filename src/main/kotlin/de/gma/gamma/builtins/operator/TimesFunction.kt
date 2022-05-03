package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.direct.GInteger
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException

class TimesFunction : BuiltinFunction(listOf("*a", "*b")) {
    override fun callInternal(scope: Scope): GValue {
        val a = getEvaluated(scope, "*a")
        val b = getEvaluated(scope, "*b")

        if (a is GInteger && b is GInteger)
            return GInteger.build(a.intValue * b.intValue)

        throw EvaluationException("+ can only be called with two integer values", builtInSource, 0, 0)
    }
}
