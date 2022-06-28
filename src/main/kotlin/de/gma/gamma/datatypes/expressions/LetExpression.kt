package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class LetExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: Identifier,
    val boundValue: Value,
    val documentation: Remark? = null
) : Expression(sourceName, beginPos, endPos) {

    override fun prettyPrint() =
        buildString {
            if (documentation != null)
                append(documentation.prettyPrint()).append(CH_NEWLINE)

            if (boundValue !is Expression) {
                append("let ${identifier.prettyPrint()} = ${boundValue.prettyPrint()}")
            } else {
                append("let ${identifier.prettyPrint()} =").append(CH_NEWLINE)
                append("    ${boundValue.prettyPrint()}")
            }
        }

    override fun evaluate(scope: Scope): Value {
        val value = boundValue.evaluate(scope)

        scope.bindValue(identifier.name, value, documentation)
        return value
    }
}
