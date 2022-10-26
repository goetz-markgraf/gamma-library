package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.checkForListOfPairs
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.RecordValue

object CopyWithFunction : BuiltinFunction(listOf("property-list", "record")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val changedValues = callParams[0].evaluate(scope)
        val record = callParams[1].evaluate(scope).toRecord(scope)

        val newValues =
            if (changedValues is RecordValue)
                changedValues.toRecord(scope)
            else {
                val checkedValue = checkForListOfPairs(changedValues.toList())
                RecordValue.build(createMapFromListOfPair(checkedValue, scope))
            }

        return record.copyWith(newValues)
    }
}
