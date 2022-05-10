package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class BooleanValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val boolValue: Boolean
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (boolValue) "true" else "false"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Boolean) = BooleanValue(builtInSource, nullPos, nullPos, value)
    }
}
