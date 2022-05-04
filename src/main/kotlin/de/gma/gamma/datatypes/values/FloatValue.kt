package de.gma.gamma.datatypes.values

import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class FloatValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val floatValue: Double
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$floatValue"

    override fun evaluate(scope: Scope) = this

}
