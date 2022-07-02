package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Scope

object PrintStarFunction : BuiltinFunction(listOf("value")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val printVal = callParams[0].evaluate(scope)

        val printString = if (printVal is ListValue && printVal !is StringValue) {
            printVal.allItems().joinToString(" ") { it.toStringValue().strValue }
        } else {
            printVal.toStringValue().strValue
        }
        print(printString)

        return printVal
    }
}
