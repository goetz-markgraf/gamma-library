package de.gma.gamma.datatypes.scoped

import de.gma.gamma.datatypes.GIdentifier
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class ScopedIdenfitier(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val id: GIdentifier,
    private val lazyScope: Scope
) : GValue(GValueType.IDENTIFIER, sourceName, beginPos, endPos) {
    private var value: GValue? = null

    override fun prettyPrint() =
        id.prettyPrint()

    override fun evaluate(scope: Scope): GValue {
        if (value == null) {
            value = id.evaluate(lazyScope)
        }

        return value ?: throw EvaluationException("must not happen", sourceName, beginPos.line, beginPos.col)
    }
}
