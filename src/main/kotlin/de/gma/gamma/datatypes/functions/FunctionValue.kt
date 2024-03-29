package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListLiteral
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.parser.Position

abstract class FunctionValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val paramNames: List<String>
) : AbstractValue(sourceName, beginPos, endPos) {

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
                val varArgs = buildList {
                    addAll(callParams.subList(0, paramNames.size - 1))
                    // We put all the rest into a ListLiteral such that items will be evaluated when list is evaluated
                    add(ListLiteral.build(callParams.subList(paramNames.size - 1, callParams.size)))
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
        else if (result is ListValue)
            result.persist()
        else
            result
    }

    private fun checkMissingParameters(callParams: List<Value>): Int {
        val expectedParams = paramNames.size
        val suppliedParams = callParams.size

        if (expectedParams == suppliedParams || isEmptyCall(callParams))
            return 0

        return expectedParams - suppliedParams
    }

    private fun isEmptyCall(callParams: List<Value>) =
        paramNames.isEmpty() && callParams.size == 1 && callParams.first() is VoidValue


    abstract fun callInternal(scope: Scope, callParams: List<Value>): Value
}
