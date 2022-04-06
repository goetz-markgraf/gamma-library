package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GOperator
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.parser.Position

class GOperation(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val operator: GOperator,
    val param1: GValue,
    val param2: GValue
) : GExpression(sourceName, beginPos, endPos) {
    override fun prettyPrint() =
        "${param1.prettyPrint()} ${operator.prettyPrint()} ${param2.prettyPrint()}"
}
