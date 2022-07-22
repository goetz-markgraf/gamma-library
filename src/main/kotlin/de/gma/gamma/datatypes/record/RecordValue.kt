package de.gma.gamma.datatypes.record

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.EmptyValue
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class RecordValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val internalMap: Map<String, Value>
) : AbstractValue(sourceName, beginPos, endPos), Namespace {

    override fun prettyPrint(): String =
        "record {${internalMap.toList().joinToString(", ") { (a, b) -> ":$a -> $b" }}}"

    override fun getValue(id: String, strict: Boolean) =
        internalMap[id]
            ?: if (strict) throw EvaluationException("Property $id not found in $this") else EmptyValue.build()

    override fun containsLocally(id: String) =
        internalMap.containsKey(id)

    fun copyWith(changedContent: List<ListValue>, scope: Scope) =
        RecordValue(
            builtInSource, nullPos, nullPos,
            mutableMapOf<String, Value>().apply {
                putAll(internalMap)
                putAll(createMapFromListOfPair(changedContent, scope))
            }
        )

    fun getPropertyNames() =
        internalMap.keys.toList()

    fun size() =
        internalMap.size

    override fun equals(other: Any?): Boolean {
        if (other !is RecordValue)
            return false

        return internalMap == other.internalMap
    }

    override fun hashCode() =
        internalMap.hashCode()
}
