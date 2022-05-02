package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class CurriedFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    params: List<GIdentifier>,
    val bakedInParams: List<GValue>,
    val function: AbstractFunction
) : AbstractFunction(sourceName, beginPos, endPos, params) {

    override fun call(scope: Scope, callParams: List<GValue>) =
        function.call(scope, bakedInParams + callParams)

    override fun callInternal(scope: Scope): GValue {
        throw EvaluationException("must not happen", sourceName, beginPos.line, beginPos.col)
    }

    override fun prettyPrint() = function.prettyPrint()

    override fun evaluate(scope: Scope) = this
}
