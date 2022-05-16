package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class FloatValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val floatValue: Double
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$floatValue"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Double) =
            FloatValue(builtInSource, nullPos, nullPos, value)
    }

    override fun equals(other: Any?) =
        if (other !is FloatValue) false
        else other.floatValue == floatValue

    override fun hashCode() = floatValue.hashCode()

}
