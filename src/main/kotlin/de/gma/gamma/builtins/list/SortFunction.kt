package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.RecordValue

object SortFunction : BuiltinFunction("sort", listOf("list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list1 = callParams[0].evaluate(scope).toList()

        return ListValue.build(list1.allItems().sortedWith(Comparator { a, b ->
            val keyA = sortKey(a)
            val keyB = sortKey(b)
            when {
                keyA is Double && keyB is Double -> keyA.compareTo(keyB)
                keyA is Double -> -1  // numbers before strings
                keyB is Double -> 1
                else -> (keyA as String).compareTo(keyB as String)
            }
        }))
    }

    private fun sortKey(item: Value): Any =
        when (item) {
            is IntegerValue -> item.longValue.toDouble()
            is FloatValue -> item.doubleValue
            is StringValue -> item.strValue.toDoubleOrNull() ?: item.strValue
            is ListValue -> item.size().toDouble()
            is RecordValue -> item.size().toDouble()
            else -> 0.0
        }
}
