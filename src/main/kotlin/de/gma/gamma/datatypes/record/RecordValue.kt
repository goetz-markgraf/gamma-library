package de.gma.gamma.datatypes.record

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.PairValue
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.Position

class RecordValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val internalMap: Map<String, Value>
) : Value(sourceName, beginPos, endPos), Namespace {

    override fun prettyPrint(): String {
        TODO("Not yet implemented")
    }

    override fun getValue(id: String) =
        internalMap[id] ?: UnitValue.build()

    fun copyWith(changedContent: List<PairValue>) =
        RecordValue(builtInSource, nullPos, nullPos,
            mutableMapOf<String, Value>().apply {
                putAll(internalMap)
                putAll(createMapFromListOfPair(changedContent))
            }
        )

    fun size() =
        internalMap.size
}
