package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.extractListOfPairFromList
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope

class CopyWithFunction : BuiltinFunction(listOf("property-list", "record")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list = callParams[0].evaluate(scope).toList()
        val record = callParams[1].evaluate(scope).toRecord()

        val checkedList = extractListOfPairFromList(list, scope)

        return record.copyWith(checkedList)
    }
}
