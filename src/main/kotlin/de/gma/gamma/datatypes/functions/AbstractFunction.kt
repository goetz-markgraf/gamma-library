package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.MapScope
import de.gma.gamma.interpreter.Scope
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
        } else if (expectedParams < suppliedParams) {
            throw EvaluationException("too many params", sourceName, beginPos.line, beginPos.col)
        } else {
            val newScope: Scope = MapScope(scope)
            paramNames.zip(callParams).map { pair ->
                newScope.bind(pair.first, pair.second.prepare(scope))
            }
            val result = callInternal(newScope)

            // save the scope for lazy evaluation
            return if (result is LambdaFunction) {
                result.prepare(newScope)
            } else result
        }
    }

    protected fun getEvaluated(scope: Scope, id: String) =
        scope.getValue(id).evaluate(scope)

    abstract fun callInternal(scope: Scope): Value
}
