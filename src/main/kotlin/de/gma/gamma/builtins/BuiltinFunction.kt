package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.AbstractFunction
import de.gma.gamma.datatypes.scope.Scope

abstract class BuiltinFunction(params: List<String>) : AbstractFunction(builtInSource, nullPos, nullPos, params) {
    override fun prettyPrint(): String = "<builtin>"

    protected fun getParamEvaluated(callParams: List<Value>, nr: Int, scope: Scope) =
        callParams[nr].evaluate(scope)

}
