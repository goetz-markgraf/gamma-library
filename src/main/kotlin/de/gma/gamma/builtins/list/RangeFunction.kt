package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListGenerator
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.IntegerValue
import java.lang.Math.abs

class RangeFunction : BuiltinFunction(listOf("from", "to")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val from = callParams[0].evaluate(scope).toInteger().longValue.toInt()
        val to = callParams[1].evaluate(scope).toInteger().longValue.toInt()

        val asc = to >= from

        val size = abs(from - to) + 1

        return ListGenerator.build(size, InternalRangeFunction(from, asc))
    }

    inner class InternalRangeFunction(
        private val from: Int,
        private val asc: Boolean
    ) : BuiltinFunction(
        listOf("pos")
    ) {
        override fun callInternal(scope: Scope, callParams: List<Value>): Value {
            val pos = callParams[0].evaluate(scope).toInteger().longValue.toInt()

            val ret = if (asc) from + pos else from - pos

            return IntegerValue.build(ret.toLong())
        }

    }

}
