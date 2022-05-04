package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.Value
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class ScopedIdenfitier(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val id: Identifier,
    private val lazyScope: Scope
) : Value(sourceName, beginPos, endPos) {
    private var value: Value? = null

    override fun prettyPrint() =
        id.prettyPrint()

    override fun evaluate(scope: Scope): Value {
        if (value == null) {
            value = id.evaluate(lazyScope)
        }

        return value ?: throw EvaluationException("must not happen", sourceName, beginPos.line, beginPos.col)
    }
}
