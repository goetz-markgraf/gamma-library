package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.scoped.ScopedFunction
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class GFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    paramNames: List<GIdentifier>,
    val expressions: List<GValue>
) : AbstractFunction(sourceName, beginPos, endPos, paramNames) {
    override fun prettyPrint() = buildString {
        append("[ ")
        if (paramNames.size == 0) {
            append("()")
        } else {
            append(paramNames.joinToString(" ") { it.prettyPrint() })
        }
        append(" ->")
        append(CH_NEWLINE)
        expressions.forEach {
            append("    ${it.prettyPrint()}").append(CH_NEWLINE)
        }
        append("]")
    }

    override fun prepare(scope: Scope) =
        ScopedFunction(sourceName, beginPos, endPos, this, scope)

    override fun callInternal(scope: Scope) =
        expressions.reduce { _, func -> func.evaluate(scope) }

}
