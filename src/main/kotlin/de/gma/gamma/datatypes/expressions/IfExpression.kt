package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class IfExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val predicate: Value,
    val thenExpr: Value,
    val elseExpr: Value
) : Expression(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "${predicate.prettyPrint()} ? ${thenExpr.prettyPrint()} : ${elseExpr.prettyPrint()}"

    override fun evaluate(scope: Scope): Value {
        val bool = predicate.evaluateToBoolean(scope).boolValue
        return if (bool)
            thenExpr.evaluate(scope)
        else
            elseExpr.evaluate(scope)
    }
}
