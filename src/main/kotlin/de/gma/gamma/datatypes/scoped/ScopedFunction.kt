package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.AbstractFunction
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class ScopedFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val function: FunctionValue,
    private val closureScope: Scope
) : AbstractFunction(sourceName, beginPos, endPos, function.paramNames) {

    override fun prettyPrint() = function.prettyPrint()

    override fun evaluate(scope: Scope) = this

    override fun call(scope: Scope, callParams: List<Value>): Value {
        return function.call(closureScope, callParams)
    }

    override fun callInternal(scope: Scope): Value {
        throw EvaluationException("must not happen", sourceName, beginPos.line, beginPos.col)
    }
}
