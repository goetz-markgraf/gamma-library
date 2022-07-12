package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

open class FunctionCall(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val function: Value,
    val params: List<Value>,
) : Expression(sourceName, beginPos, endPos) {
    override fun prettyPrint(): String {
        val expressions = params.map { Pair(it is Expression && it !is Block, it.prettyPrint()) }

        return "${function.prettyPrint()} ${expressions.joinToString(" ") { if (it.first) "(${it.second})" else it.second }}"
    }

    override fun evaluate(scope: Scope): Value {
        val functionToCall = function.evaluate(scope).toFunction()

        try {
            return functionToCall.call(scope, params)
        } catch (e: EvaluationException) {
            e.add(
                StackTraceElement(
                    "gamma",
                    "Function(${beginPos.line + 1}:${beginPos.col + 1})",
                    sourceName,
                    beginPos.line + 1
                )
            )
            throw e
        }
    }

}
