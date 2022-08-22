package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class VoidValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position
) : AbstractValue(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "()"

    override fun evaluate(scope: Scope) = this

    companion object {
        private val singleton = VoidValue(builtInSource, nullPos, nullPos)
        fun build() = singleton
    }

    override fun equals(other: Any?) =
        other is VoidValue

    override fun hashCode() = Unit.hashCode()

}
