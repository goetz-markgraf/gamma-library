package de.gma.gamma.datatypes.values

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class RecordValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val internalMap: Map<String, Value>
) : AbstractValue(sourceName, beginPos, endPos), Namespace {

    override fun prettyPrint(): String =
        "{${internalMap.toList().joinToString(", ") { (a, b) -> ":$a -> $b" }}}"

    override fun getValueForName(id: String, strict: Boolean) =
        internalMap[id]
            ?: if (strict) throw EvaluationException("Property $id not found in $this") else VoidValue.build()

    override fun containsNameLocally(id: String) =
        internalMap.containsKey(id)

    fun copyWith(changedContent: RecordValue) =
        RecordValue(
            builtInSource, nullPos, nullPos,
            mutableMapOf<String, Value>().apply {
                putAll(internalMap)
                putAll(changedContent.internalMap)
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

    fun convertToList() =
        ListValue.build(
            internalMap.entries.map {
                ListValue.build(listOf(PropertyValue.build(it.key), it.value))
            })

    override fun hashCode() =
        internalMap.hashCode()

    companion object {
        fun buildEmpty() = RecordValue(builtInSource, nullPos, nullPos, emptyMap())
        fun build(map: Map<String, Value>) = RecordValue(builtInSource, nullPos, nullPos, map)
    }
}
