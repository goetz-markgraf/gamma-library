package de.gma.gamma.datatypes.values

import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class PropertyValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() = ":$identifier"

    override fun evaluate(scope: Scope) = this

}
