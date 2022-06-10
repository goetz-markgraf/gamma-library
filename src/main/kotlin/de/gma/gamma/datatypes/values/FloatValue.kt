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
    val doubleValue: Double
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$doubleValue"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Double) =
            FloatValue(builtInSource, nullPos, nullPos, value)
    }

    override fun equals(other: Any?) =
        when (other) {
            is IntegerValue -> other.toFloat().doubleValue == doubleValue
            is FloatValue -> other.doubleValue == doubleValue
            else -> false
        }

    override fun hashCode() = doubleValue.hashCode()

}
