package de.gma.gamma.builtins.assertions

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.parser.EvaluationException

object AssertFunction : BuiltinFunction(listOf("list-of-assertions")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val items = callParams[0].evaluate(scope).toList()

        var message = ""
        var ret = true

        items.allItems().forEach {
            if (it is StringValue)
                message = it.strValue
            else if (it is ListValue && it.size() == 2) {
                val actual = it.first().evaluate(scope)
                val expected = it.last().evaluate(scope)
                if (actual != expected) {
                    println("Assertion Failure $message: Value $actual is not equal to $expected")
                    ret = false
                }
            } else
                throw EvaluationException("item $it is neither a string nor a pair")
        }

        return BooleanValue.build(ret)
    }
}
