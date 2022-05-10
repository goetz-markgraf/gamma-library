package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.scoped.ScopedIdentifier
import de.gma.gamma.parser.Position

class Identifier(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val name: String,
    val identifierType: GIdentifierType
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() =
        when (identifierType) {
            GIdentifierType.ID -> name
            GIdentifierType.OP -> name
            GIdentifierType.OP_AS_ID -> "($name)"
            GIdentifierType.ID_AS_OP -> "$name:"
        }

    override fun evaluate(scope: Scope): Value {
        if (name.contains('.')) {
            TODO("compound identifier not yet implemented")
        } else {
            return scope.getValue(name).evaluate(scope)
        }
    }

    override fun prepare(scope: Scope) =
        ScopedIdentifier(sourceName, beginPos, endPos, this, scope)
}

enum class GIdentifierType {
    ID,
    OP,
    ID_AS_OP,
    OP_AS_ID
}
