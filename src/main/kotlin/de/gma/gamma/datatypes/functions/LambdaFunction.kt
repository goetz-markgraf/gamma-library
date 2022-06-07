package de.gma.gamma.datatypes.functions

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.scoped.ScopedFunction
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.CH_NEWLINE
import de.gma.gamma.parser.Position

class LambdaFunction(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
    paramNames: List<String>,
    val expressions: List<Value>
) : FunctionValue(sourceName, beginPos, endPos, paramNames) {
    override fun prettyPrint() = buildString {
        append("[")
        if (paramNames.isEmpty()) {
            append("()")
        } else {
            append(paramNames.joinToString(" "))
        }

        if (expressions.size == 0) {
            append(" -> ")
        } else if (expressions.size == 1) {
            append(" -> ")
            append(expressions.first().prettyPrint())
        } else {
            append(" ->").append(CH_NEWLINE)
            expressions.forEach {
                append("    ${it.prettyPrint()}").append(CH_NEWLINE)
            }
        }
        append("]")
    }

    override fun prepare(scope: Scope) =
        ScopedFunction(sourceName, beginPos, endPos, this, scope)

    override fun evaluate(scope: Scope) =
        ScopedFunction(sourceName, beginPos, endPos, this, scope)

    override fun callInternal(scope: Scope, callParams: List<Value>): Value {

        val newScope: Scope = ModuleScope(scope)
        paramNames.zip(callParams).map { pair ->
            newScope.bind(pair.first, pair.second)
        }

        return expressions.fold(UnitValue.build() as Value) { _, expr ->
            expr.evaluate(newScope)
        }
    }
}
