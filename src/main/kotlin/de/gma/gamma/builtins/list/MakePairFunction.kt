package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.PairValue
import de.gma.gamma.parser.GammaException

object MakePairFunction : BuiltinFunction("make-pair", listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val p = callParams[0].evaluate(scope).toList()

        if (p.size() == 2) return PairValue.build(p.first(), p.last())
        else throw GammaException("${p.prettyPrint()} cannot be made into a pair")
    }
}
