package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue

object SortDescendingFunction : BuiltinFunction(listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list1 = callParams[0].evaluate(scope).toList()

        return ListValue.build(list1.allItems().sortedByDescending { item ->
            when (item) {
                is IntegerValue -> item.longValue.toDouble()
                is FloatValue -> item.doubleValue
                is StringValue -> item.toFloat().doubleValue
                is ListValue -> item.size().toDouble()
                is RecordValue -> item.size().toDouble()
                else -> 0.0
            }
        })
    }
}
