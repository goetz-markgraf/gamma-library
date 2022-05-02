package de.gma.gamma.datatypes.direct

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

class GProperty(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String
) : GValue(GValueType.PROPERTY, sourceName, beginPos, endPos) {

    override fun prettyPrint() = ":$identifier"

    override fun evaluate(scope: Scope) = this

}
