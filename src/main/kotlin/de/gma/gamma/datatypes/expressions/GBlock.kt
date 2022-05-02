package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class GBlock(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val expressions: List<GValue>
) : GExpression(sourceName, beginPos, endPos) {

    override fun prettyPrint() = buildString {
        append("(").append(CH_NEWLINE)
        expressions.forEach {
            append("    ${it.prettyPrint()}").append(CH_NEWLINE)
        }
        append(")")
    }

    override fun evaluate(scope: Scope): GValue {
        return expressions.reduce { _, v -> v.evaluate(scope) }
    }
}
