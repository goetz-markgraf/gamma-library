package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

open class FunctionCall(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val function: Value,
    val params: List<Value>,
) : Expression(sourceName, beginPos, endPos) {
    override fun prettyPrint() =
        "${function.prettyPrint()} ${params.joinToString(" ") { it.prettyPrint() }}"

    override fun evaluate(scope: Scope): Value {
        val functionToCall = function.evaluateToFunction(scope)

        return functionToCall.call(scope, params)
    }

}
