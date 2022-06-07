package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListGenerator
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue

class MapFunction : BuiltinFunction(listOf("function", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val function = callParams[0].evaluate(scope).toFunction()
        val list = callParams[1].evaluate(scope).toList()

        return ListGenerator.build(list.size(), InternalMapFunction(list, function))
    }

    inner class InternalMapFunction(
        private val list: ListValue,
        private val function: FunctionValue
    ) : BuiltinFunction(
        listOf("pos")
    ) {
        override fun callInternal(scope: Scope, callParams: List<Value>): Value {
            val pos = callParams[0].evaluate(scope).toInteger().intValue.toInt()

            val item = list.getAt(pos)
            if (item is UnitValue)
                return item

            val params = listOf(item)
            return function.call(scope, params)
        }

    }
}
