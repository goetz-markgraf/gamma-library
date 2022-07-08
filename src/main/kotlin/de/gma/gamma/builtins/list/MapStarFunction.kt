package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListGenerator
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue

object MapStarFunction : BuiltinFunction(listOf("function", "list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val function = callParams[0].evaluate(scope).toFunction()
        val list = callParams[1].evaluate(scope).toList()

        return ListGenerator.build(list.size(), InternalMapStarFunction(list, function))
    }

}

private class InternalMapStarFunction(
    private val list: ListValue,
    private val function: FunctionValue
) : BuiltinFunction(
    listOf("pos")
) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val pos = callParams[0].evaluate(scope).toInteger()

        val item = list.getAt(pos.longValue.toInt())
        if (item is UnitValue)
            return item

        val params = listOf(item, pos)
        return function.call(scope, params)
    }

}
