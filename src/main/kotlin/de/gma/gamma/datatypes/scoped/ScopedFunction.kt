package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.functions.LambdaFunction
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class ScopedFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val function: LambdaFunction,
    private val closureScope: Scope
) : FunctionValue(sourceName, beginPos, endPos, function.paramNames) {

    override fun prettyPrint() = function.prettyPrint()

    override fun evaluate(scope: Scope) = this

    override fun call(scope: Scope, callParams: List<Value>): Value {
        // Wrap each argument in a ScopedValue so it captures the caller's scope lazily.
        // The function body then runs in closureScope (the scope from lambda definition),
        // but each argument is evaluated on demand in the scope where the call was made.
        val preparedParams = callParams.map { it.prepare(scope) }
        return function.call(closureScope, preparedParams)
    }

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        throw createException("must not happen")
    }
}
