package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.builtins.isRecordDefinition
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.scoped.ScopedValue
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

private const val ERROR_MESSAGE = "ListLiteral cannot be processed"

class ListLiteral(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    items: List<Value>
) : ListValue(sourceName, beginPos, endPos) {

    protected var internalItems = items
    private val internalSize = internalItems.size

    override fun evaluate(scope: Scope): Value {
        val items = internalItems.map {
            it.evaluate(scope)
        }
        return when {
            isRecordDefinition(items) -> RecordValue(
                this.sourceName, this.beginPos, this.endPos, createMapFromListOfPair(
                    items as List<ListValue>, scope
                )
            )
            items.size == 2 -> PairValue(sourceName, beginPos, endPos, items[0], items[1])
            else -> SimpleList(sourceName, beginPos, endPos, items)
        }
    }

    override fun prepare(scope: Scope) =
        ScopedValue(sourceName, beginPos, endPos, this, scope)

    override fun size() = internalSize

    override fun getAt(pos: Int): Value =
        if (pos >= 0 && pos < internalSize)
            internalItems[pos]
        else
            if (internalSize > 0)
                throw createException("Index out of bounds: $pos outside [0..${internalSize - 1}]")
            else
                throw createException("Index out of bounds: $pos outside empty list")

    override fun slice(from: Int, length: Int): ListValue {
        throw EvaluationException(ERROR_MESSAGE)
    }

    override fun allItems(): List<Value> =
        internalItems

    override fun append(v: Value): ListValue {
        throw EvaluationException(ERROR_MESSAGE)
    }


    override fun insertFirst(v: Value): ListValue {
        throw EvaluationException(ERROR_MESSAGE)
    }


    override fun appendAll(v: ListValue): ListValue {
        throw EvaluationException(ERROR_MESSAGE)
    }


    override fun equals(other: Any?) =
        other is ListValue && other.allItems() == allItems()

    override fun hashCode() = internalItems.hashCode()

    companion object {
        fun build(items: List<Value>) =
            ListLiteral(builtInSource, nullPos, nullPos, items)
    }
}
