package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

class ScopedValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val v: Value,
    private val lazyScope: Scope
) : Value(sourceName, beginPos, endPos) {
    private var value: Value? = null

    override fun prettyPrint() = "Lazy: ${v.prettyPrint()}"

    override fun evaluate(scope: Scope): Value {
        if (value == null) {
            value = v.evaluate(lazyScope)
        }

        return value ?: throw createException("must not happen")
    }
}
