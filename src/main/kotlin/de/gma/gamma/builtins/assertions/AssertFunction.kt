package de.gma.gamma.builtins.assertions

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.datatypes.values.PairValue
import de.gma.gamma.parser.EvaluationException

object AssertFunction : BuiltinFunction(listOf("list-of-assertions")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val tempScope = ModuleScope(sourceName, scope)
        val items = callParams[0].evaluate(tempScope).toList()

        var message = ""
        var ret = true

        items.allItems().forEach {
            if (it is StringValue)
                message = it.strValue
            else if (it is PairValue) {
                val actual = it.first().evaluate(tempScope)
                val expected = it.last().evaluate(tempScope)
                if (actual != expected) {
                    GammaBaseScope.output.println("Assertion Failure $message: Value $actual is not equal to $expected")
                    ret = false
                }
            } else
                throw EvaluationException("item $it is neither a string nor a pair")
        }

        return BooleanValue.build(ret)
    }
}
