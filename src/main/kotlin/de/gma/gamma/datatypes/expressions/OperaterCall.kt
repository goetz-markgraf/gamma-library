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

    override fun prettyPrint(): String {
        val levelOp1 = op1 is OperaterCall && op1.level > level
        val levelOp2 = op2 is OperaterCall && op2.level > level

        val op1PP = if (levelOp1) "(${op1.prettyPrint()})" else op1.prettyPrint()
        val op2PP = if (levelOp2) "(${op2.prettyPrint()})" else op2.prettyPrint()

        return "$op1PP ${operator.prettyPrint()} $op2PP"
    }

    companion object {
        fun build(operator: Identifier, op1: Value, op2: Value) =
            OperaterCall(builtInSource, nullPos, nullPos, operator, op1, op2)
    }
}
