package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class UnitValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "()"

    override fun evaluate(scope: Scope) = this

    companion object {
        private val singleton = UnitValue(builtInSource, nullPos, nullPos)
        fun build() = singleton
    }

    override fun equals(other: Any?) =
        other is UnitValue

    override fun hashCode() = Unit.hashCode()

}
