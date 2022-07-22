package de.gma.gamma.datatypes.values

import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class PropertyValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String
) : AbstractValue(sourceName, beginPos, endPos) {

    override fun prettyPrint() = ":$identifier"

    override fun evaluate(scope: Scope) = this

    override fun equals(other: Any?) =
        if (other !is PropertyValue) false
        else other.identifier == identifier

    override fun hashCode() = identifier.hashCode()

}
