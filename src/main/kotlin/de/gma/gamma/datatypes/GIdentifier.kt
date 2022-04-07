package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class GIdentifier(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val identifier: String,
    val identifierType: GIdentifierType
) : GValue(GValueType.IDENTIFIER, sourceName, beginPos, endPos) {

    override fun prettyPrint() =
        when (identifierType) {
            GIdentifierType.ID -> identifier
            GIdentifierType.OP -> identifier
            GIdentifierType.OP_AS_ID -> "($identifier)"
            GIdentifierType.ID_AS_OP -> "$identifier:"
        }
}

enum class GIdentifierType {
    ID,
    OP,
    ID_AS_OP,
    OP_AS_ID
}
