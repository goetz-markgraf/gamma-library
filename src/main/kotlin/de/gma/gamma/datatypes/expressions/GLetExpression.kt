package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.direct.GRemark
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class GLetExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: GIdentifier,
    val boundValue: GValue,
    val documentation: GRemark? = null
) : GExpression(sourceName, beginPos, endPos) {

    override fun prettyPrint() =
        buildString {
            if (documentation != null)
                append(documentation.prettyPrint()).append(CH_NEWLINE)

            if (boundValue.type != GValueType.EXPRESSION) {
                append("let ${identifier.prettyPrint()} = ${boundValue.prettyPrint()}")
            } else {
                append("let ${identifier.prettyPrint()} =").append(CH_NEWLINE)
                append("    ${boundValue.prettyPrint()}")
            }
            append(CH_NEWLINE)
        }

    override fun evaluate(scope: Scope): GValue {
        val value = boundValue.prepare(scope)

        scope.bind(identifier.name, value, documentation)
        return value
    }
}
