package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.Position

abstract class FunctionValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val paramNames: List<String>
) : Value(sourceName, beginPos, endPos) {

    open fun call(scope: Scope, callParams: List<Value>): Value {
        val missing = checkMissingParameters(callParams)

        return when {
            missing > 0 -> CurriedFunction(
                sourceName,
                beginPos,
                endPos,
                paramNames.drop(paramNames.size - missing),
                scope,
                callParams,
                this
            )
            missing < 0 -> {
                val varParamsSize = missing
                val varArgs = buildList {
                    addAll(callParams.subList(0, paramNames.size - 1))
                    add(ListValue.build(callParams.subList(paramNames.size - 1, callParams.size)))
                }

                doCallAndPrepareReturn(scope, varArgs)
            }
            else -> doCallAndPrepareReturn(scope, callParams)
        }
    }

    private fun doCallAndPrepareReturn(
        scope: Scope,
        params: List<Value>,
    ): Value {
        val result = callInternal(scope, params)

        // save the scope for lazy evaluation
        return if (result is LambdaFunction)
            result.prepare(scope)
        else
            result
    }

    private fun checkMissingParameters(callParams: List<Value>): Int {
        val expectedParams = paramNames.size
        val suppliedParams = callParams.size

        if (expectedParams == suppliedParams || isUnitCall(callParams))
            return 0

        return expectedParams - suppliedParams
    }

    private fun isUnitCall(callParams: List<Value>) =
        paramNames.isEmpty() && callParams.size == 1 && callParams.first() is UnitValue


    abstract fun callInternal(scope: Scope, callParams: List<Value>): Value
}
