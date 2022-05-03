package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GIfExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val predicate: GValue,
    val thenExpr: GValue,
    val elseExpr: GValue
) : GExpression(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "${predicate.prettyPrint()} ? ${thenExpr.prettyPrint()} : ${elseExpr.prettyPrint()}"

    override fun evaluate(scope: Scope): GValue {
        val bool = predicate.evaluateToBoolean(scope).boolValue
        return if (bool)
            thenExpr.evaluate(scope)
        else
            elseExpr.evaluate(scope)
    }
}
