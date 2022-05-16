package de.gma.gamma.datatypes.values

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class StringValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val strValue: String
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = "\"$strValue\""

    override fun evaluate(scope: Scope) = this

    override fun equals(other: Any?) =
        if (other !is StringValue) false
        else other.strValue == strValue

    override fun hashCode() = strValue.hashCode()

}
