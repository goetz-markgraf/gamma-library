package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class BooleanValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val boolValue: Boolean
) : AbstractValue(sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (boolValue) "true" else "false"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Boolean) = BooleanValue(builtInSource, nullPos, nullPos, value)
    }

    override fun equals(other: Any?) =
        if (other !is BooleanValue) false
        else other.boolValue == boolValue

    override fun hashCode() = boolValue.hashCode()

}
