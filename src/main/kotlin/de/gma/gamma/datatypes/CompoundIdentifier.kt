package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.scoped.ScopedValue
import de.gma.gamma.parser.Position

class CompoundIdentifier(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    val names: List<String>
) : Value(sourceName, beginPos, endPos) {

    override fun prettyPrint() =
        names.joinToString(".")

    override fun evaluate(scope: Scope) =
        names.fold(scope as Any) { namespace, item ->
            (namespace as Namespace).getValue(item, strict = true).evaluate(scope)
        } as Value

    override fun prepare(scope: Scope) =
        ScopedValue(sourceName, beginPos, endPos, this, scope)

    override fun equals(other: Any?) =
        other is CompoundIdentifier && other.names == names

    override fun hashCode() = names.hashCode()

}
