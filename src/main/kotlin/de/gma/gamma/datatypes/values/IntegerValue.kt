package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class IntegerValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val longValue: Long
) : AbstractValue(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$longValue"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Long) =
            IntegerValue(builtInSource, nullPos, nullPos, value)
    }

    override fun equals(other: Any?) =
        when (other) {
            is IntegerValue -> other.longValue == longValue
            is FloatValue -> other.toInteger().longValue == longValue
            else -> false
        }

    override fun hashCode() = longValue.hashCode()
}
