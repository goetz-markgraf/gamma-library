package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.MapScope
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

abstract class AbstractFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val paramNames: List<GIdentifier>
) : GValue(GValueType.FUNCTION, sourceName, beginPos, endPos) {

    open fun call(scope: Scope, callParams: List<GValue>): GValue {
        val expectedParams = paramNames.size
        val suppliedParams = callParams.size

        return if (expectedParams < suppliedParams) {
            CurriedFunction(sourceName, beginPos, endPos, paramNames, callParams, this)
        } else if (expectedParams > suppliedParams) {
            throw EvaluationException("too many params", sourceName, beginPos.line, beginPos.col)
        } else {
            val newScope: Scope = MapScope(scope)
            paramNames.zip(callParams).map { pair ->
                newScope.bind(pair.first.name, pair.second.prepare(scope))
            }
            val result = callInternal(newScope)

            // save the scope for lazy evaluation
            return result.prepare(newScope)
        }
    }

    abstract fun callInternal(scope: Scope): GValue
}
