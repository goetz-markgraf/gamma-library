package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.expressions.OperaterCall
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.EvaluationException

class WhenFunction : BuiltinFunction(listOf("cases")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val cases = callParams[0].evaluate(scope).toList()

        var result: Value = UnitValue.build()

        var pos = 0
        while (pos < cases.size()) {
            var predicate: Value
            var expression: Value

            val nextItem = cases.getAt(pos++)
            if (nextItem is OperaterCall && nextItem.operator.name == "->") {
                predicate = nextItem.op1
                expression = nextItem.op2
            } else if (nextItem is ListValue && nextItem.size() == 2) {
                predicate = nextItem.first()
                expression = nextItem.last()
            } else if (pos < cases.size()) {
                predicate = nextItem
                expression = cases.getAt(pos++)
            } else if (pos == cases.size()) {
                result = nextItem.evaluate(scope)
                break
            } else {
                throw EvaluationException("wrong parameters for when function")
            }

            if (predicate.evaluate(scope).toBoolean().boolValue) {
                result = expression.evaluate(scope)
                break
            }
        }

        return result
    }
}
