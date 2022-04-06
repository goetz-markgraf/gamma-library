package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.parser.Position

class GFunctionCall(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val functionName: GValue,
    val params: List<GValue>
) : GExpression(sourceName, beginPos, endPos) {
    override fun prettyPrint(): String {
        TODO("Not yet implemented")
    }
}
