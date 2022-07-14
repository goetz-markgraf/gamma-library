package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.scoped.ScopedValue
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

    override fun evaluate(scope: Scope) =
        scope.getValue(name, strict = true).evaluate(scope)

    override fun prepare(scope: Scope) =
        ScopedValue(sourceName, beginPos, endPos, this, scope)

    override fun equals(other: Any?) =
        other is Identifier && other.name == name

    override fun hashCode() = name.hashCode()

}

enum class GIdentifierType {
    ID,
    OP,
    ID_AS_OP,
    OP_AS_ID
}
