package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class IntegerValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val intValue: Long
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "$intValue"

    override fun evaluate(scope: Scope) = this

    companion object {
        fun build(value: Long) =
            IntegerValue(builtInSource, nullPos, nullPos, value)
    }
}
