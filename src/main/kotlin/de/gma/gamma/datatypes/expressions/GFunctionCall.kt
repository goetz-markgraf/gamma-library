package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GIdentifierType
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GFunctionCall(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val function: GValue,
    val params: List<GValue>,
) : GExpression(sourceName, beginPos, endPos) {
    override fun prettyPrint() =
        if (isOperation()) {
            "${op1?.prettyPrint()} ${function.prettyPrint()} ${op2?.prettyPrint()}"
        } else {
            "${function.prettyPrint()} ${params.joinToString(" ") { it.prettyPrint() }}"
        }

    override fun evaluate(scope: Scope): GValue {
        val functionToCall = function.evaluateToFunction(scope)

        return functionToCall.call(scope, params)
    }

    val op1: GValue?
        get() =
            if (isOperation() && params.size >= 2) params[0] else null

    val op2: GValue?
        get() =
            if (isOperation() && params.size >= 2) params[1] else null

    private fun isOperation() =
        function is GIdentifier &&
                (function.identifierType == GIdentifierType.OP ||
                        function.identifierType == GIdentifierType.ID_AS_OP)


    companion object {
        fun fromOperation(
            sourceName: String,
            beginPos: Position,
            endPos: Position,
            function: GValue,
            op1: GValue,
            op2: GValue
        ) = GFunctionCall(sourceName, beginPos, endPos, function, listOf(op1, op2))
    }
}
