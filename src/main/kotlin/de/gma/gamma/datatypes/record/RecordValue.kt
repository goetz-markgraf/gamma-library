package de.gma.gamma.datatypes.record

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

class RecordValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val internalMap: Map<String, Value>
) : Value(sourceName, beginPos, endPos), Namespace {

    override fun prettyPrint(): String =
        "record {${internalMap.toList().joinToString(", ") { (a, b) -> ":$a -> $b" }}}"

    override fun getValue(id: String) =
        internalMap[id] ?: throw EvaluationException("Property $id not found in $this")

    fun copyWith(changedContent: List<ListValue>, scope: Scope) =
        RecordValue(
            builtInSource, nullPos, nullPos,
            mutableMapOf<String, Value>().apply {
                putAll(internalMap)
                putAll(createMapFromListOfPair(changedContent, scope))
            }
        )

    fun size() =
        internalMap.size
}
