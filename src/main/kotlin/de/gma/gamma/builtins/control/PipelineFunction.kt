package de.gma.gamma.builtins.control

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.EmptyValue
import de.gma.gamma.parser.EvaluationException

object PipelineFunction : BuiltinFunction(listOf("id", "expressions")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val identifier = callParams[0]
        val expressions = callParams[1]

        if (expressions !is ListValue)
            throw EvaluationException("$expressions is not a list of expressions")

        val idStr =
            if (identifier is Identifier) identifier.name
            else if (identifier is StringValue) identifier.strValue
            else throw EvaluationException("$identifier is not an identifier")

        val pipelineScope = ModuleScope(sourceName, scope)
        var result: Value = EmptyValue.build()

        expressions.allItems().forEach { expr ->
            pipelineScope.setValue(idStr, result, strict = false)
            result = expr.evaluate(pipelineScope)
        }

        return result
    }
}
