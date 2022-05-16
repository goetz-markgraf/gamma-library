package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

abstract class AbstractFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val paramNames: List<String>
) : Value(sourceName, beginPos, endPos) {

    open fun call(scope: Scope, callParams: List<Value>): Value {
        val expectedParams = paramNames.size
        val suppliedParams = callParams.size

        return if (expectedParams > suppliedParams) {
            CurriedFunction(sourceName, beginPos, endPos, paramNames.drop(suppliedParams), scope, callParams, this)
        } else if (expectedParams < suppliedParams && !isUnitCall(callParams)) {
            throw EvaluationException("too many params", sourceName, beginPos.line, beginPos.col)
        } else {
            val newScope: Scope = ModuleScope(scope)
            if (!isUnitCall(callParams)) {
                paramNames.zip(callParams).map { pair ->
                    newScope.bind(pair.first, pair.second.prepare(scope))
                }
            }
            val result = callInternal(newScope)

            // save the scope for lazy evaluation
            return if (result is FunctionValue) {
                result.prepare(newScope)
            } else result
        }
    }

    private fun isUnitCall(callParams: List<Value>) =
        paramNames.isEmpty() && callParams.size == 1 && callParams.first() is UnitValue

    protected fun getEvaluated(scope: Scope, id: String) =
        scope.getValue(id).evaluate(scope)

    abstract fun callInternal(scope: Scope): Value
}
