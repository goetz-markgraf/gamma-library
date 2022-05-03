package de.gma.gamma.datatypes.direct

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GBoolean(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val boolValue: Boolean
) : GValue(GValueType.STRING, sourceName, beginPos, endPos) {

    override fun prettyPrint() = if (boolValue) "true" else "false"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Boolean) = GBoolean(builtInSource, nullPos, nullPos, value)
    }
}
