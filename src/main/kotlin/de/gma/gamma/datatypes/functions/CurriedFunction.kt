package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class CurriedFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    params: List<String>,
    val savedScope: Scope,
    val bakedInParams: List<Value>,
    val function: FunctionValue
) : FunctionValue(sourceName, beginPos, endPos, params) {

    override fun call(scope: Scope, callParams: List<Value>): Value {
        val preparedCallParams = callParams.map { it.prepare(scope) }
        return function.call(savedScope, bakedInParams + preparedCallParams)
    }

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        throw createException("must not happen")
    }

    override fun prettyPrint() =
        function.prettyPrint() + " " + bakedInParams.joinToString(" ") { it.prettyPrint() }

    override fun evaluate(scope: Scope) = this
}
