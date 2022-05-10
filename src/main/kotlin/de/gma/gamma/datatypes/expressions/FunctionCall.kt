package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GIdentifierType
import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class FunctionCall(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val function: Value,
    val params: List<Value>,
) : Expression(sourceName, beginPos, endPos) {
    override fun prettyPrint() =
        if (isOperation()) {
            "${op1?.prettyPrint()} ${function.prettyPrint()} ${op2?.prettyPrint()}"
        } else {
            "${function.prettyPrint()} ${params.joinToString(" ") { it.prettyPrint() }}"
        }

    override fun evaluate(scope: Scope): Value {
        val functionToCall = function.evaluateToFunction(scope)

        return functionToCall.call(scope, params)
    }

    val op1: Value?
        get() =
            if (isOperation() && params.size >= 2) params[0] else null

    val op2: Value?
        get() =
            if (isOperation() && params.size >= 2) params[1] else null

    private fun isOperation() =
        function is Identifier &&
                (function.identifierType == GIdentifierType.OP ||
                        function.identifierType == GIdentifierType.ID_AS_OP)


    companion object {
        fun fromOperation(
            sourceName: String,
            beginPos: Position,
            endPos: Position,
            function: Value,
            op1: Value,
            op2: Value
        ) = FunctionCall(sourceName, beginPos, endPos, function, listOf(op1, op2))
    }
}
