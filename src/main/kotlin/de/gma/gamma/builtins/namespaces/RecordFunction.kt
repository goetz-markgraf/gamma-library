package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.builtins.extractListOfPairFromList
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope

class RecordFunction : BuiltinFunction(listOf("property-list")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val list = callParams[0].evaluate(scope).toList()

        val checkedList = extractListOfPairFromList(list, scope)

        return RecordValue(list.sourceName, list.beginPos, list.endPos, createMapFromListOfPair(checkedList))
    }


}
