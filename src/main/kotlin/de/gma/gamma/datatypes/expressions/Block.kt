package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.Position

class Block(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val expressions: List<Value>
) : Expression(sourceName, beginPos, endPos) {

    override fun prettyPrint() = buildString {
        append("(")
        append(expressions.joinToString(", ") { it.prettyPrint() })
        append(")")
    }

    override fun evaluate(scope: Scope) =
        expressions.fold(UnitValue.build() as Value) { _, expr -> expr.evaluate(scope) }

}
