package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.scoped.ScopedValue
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class PairValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val first: Value,
    private val last: Value,
    private val evaluated: Boolean = false
) : AbstractValue(sourceName, beginPos, endPos), Namespace {
    override fun prettyPrint() =
        "${first.prettyPrint()} → ${last.prettyPrint()}"

    override fun getValueForName(id: String, strict: Boolean) =
        when (id) {
            "first" -> first
            "last" -> last
            else -> if (strict) throw EvaluationException("Property $id not found in $this") else VoidValue.build()
        }

    override fun containsNameLocally(id: String) =
        id == "first" || id == "last"

    fun first() = first

    fun last() = last

    override fun evaluate(scope: Scope) =
        if (evaluated) this
        else PairValue(sourceName, beginPos, endPos, first.evaluate(scope), last.evaluate(scope))

    override fun prepare(scope: Scope) =
        if (evaluated) this
        else ScopedValue(sourceName, beginPos, endPos, this, scope)

    override fun equals(other: Any?) =
        other is PairValue && other.first == first() && other.last == last

    override fun hashCode(): Int {
        return (first.hashCode() + last.hashCode()).hashCode()
    }

    companion object {
        fun build(first: Value, last: Value) = PairValue(builtInSource, nullPos, nullPos, first, last)
    }
}
