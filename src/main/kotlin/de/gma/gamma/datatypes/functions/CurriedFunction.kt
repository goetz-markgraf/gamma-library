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
    val function: AbstractFunction
) : AbstractFunction(sourceName, beginPos, endPos, params) {

    override fun call(scope: Scope, callParams: List<Value>) =
        function.call(savedScope, bakedInParams + callParams)

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        throw createException("must not happen")
    }

    override fun prettyPrint() = function.prettyPrint()

    override fun evaluate(scope: Scope) = this
}
