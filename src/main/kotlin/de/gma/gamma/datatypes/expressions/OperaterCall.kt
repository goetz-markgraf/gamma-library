package de.gma.gamma.datatypes.expressions

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.Value
import de.gma.gamma.parser.Position
import de.gma.gamma.parser.operatorLevel

class OperaterCall(
    source: String,
    begin: Position,
    end: Position,
    val operator: Identifier,
    val op1: Value,
    val op2: Value,
    val level: Int = operatorLevel(operator.name)
) : FunctionCall(source, begin, end, operator, listOf(op1, op2)) {

    override fun prettyPrint() =
        "${op1.prettyPrint()} ${operator.prettyPrint()} ${op2.prettyPrint()}"

    companion object {
        fun build(operator: Identifier, op1: Value, op2: Value) =
            OperaterCall(builtInSource, nullPos, nullPos, operator, op1, op2)
    }
}
