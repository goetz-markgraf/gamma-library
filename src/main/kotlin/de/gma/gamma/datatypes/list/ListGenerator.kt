package de.gma.gamma.datatypes.list

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.parser.Position

class ListGenerator(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    private val size: Int,
    private val generatorFunction: FunctionValue
) : ListValue(sourceName, beginPos, endPos) {

    private val internalScope = ModuleScope(sourceName)

    private val storage = Array<Value?>(size) { null }

    override fun allItems(): List<Value> {
        storage.forEachIndexed { index, _ -> fillStorage(index) }
        return storage.map { it!! }
    }

    override fun getAt(pos: Int): Value {
        if (pos >= size)
            return VoidValue.build()

        if (storage[pos] == null)
            fillStorage(pos)

        return storage[pos]!!
    }

    override fun size() = size

    override fun slice(from: Int, length: Int): ListValue {
        if (from > size || from < 0)
            return buildEmpty()

        val correctLength = if (from + length > size)
            size - from
        else length

        return SubList.buildSublist(this, dropFirst = from, dropLast = size - from - correctLength)

    }

    override fun append(v: Value): ListValue =
        build(buildList {
            addAll(allItems())
            add(v)
        })

    override fun insertFirst(v: Value): ListValue =
        build(buildList {
            add(v)
            addAll(allItems())
        })

    override fun appendAll(v: ListValue): ListValue =
        build(buildList {
            addAll(v.allItems())
            addAll(allItems())
        })


    private fun fillStorage(pos: Int) {
        if (storage[pos] != null)
            return

        val params = listOf(IntegerValue.build(pos.toLong()))

        storage[pos] = generatorFunction.call(internalScope, params)
    }

    override fun persist(): ListValue =
        build(allItems())

    companion object {
        fun build(size: Int, function: FunctionValue) = ListGenerator(builtInSource, nullPos, nullPos, size, function)
    }
}
