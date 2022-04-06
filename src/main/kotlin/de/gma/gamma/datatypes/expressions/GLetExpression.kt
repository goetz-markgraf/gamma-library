package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.parser.Position

class GLetExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: GIdentifier,
    val boundValue: GValue
) : GExpression(sourceName, beginPos, endPos) {

    override fun prettyPrint() =
        if (boundValue.type != GValueType.EXPRESSION) {
            "let ${identifier.prettyPrint()} = ${boundValue.prettyPrint()}"
        } else {
            "let ${identifier.prettyPrint()} = ${boundValue.prettyPrint()}"
        }

}
