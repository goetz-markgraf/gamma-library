package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.parser.EvaluationException

fun extractNumber(value: Value) =
    when {
        value is IntegerValue || value is FloatValue -> value
        value is StringValue -> extractNumberFromString(value)
        else -> VoidValue.build()
    }

private fun extractNumberFromString(value: StringValue): Value {
    try {
        return value.toInteger()
    } catch (e: EvaluationException) {
        // do nothing
    }
    try {
        return value.toFloat()
    } catch (e: EvaluationException) {
        // do nothing
    }
    return VoidValue.build()
}

fun checkForListOfPairs(
    list: ListValue,
) = list.allItems().map {
    if (it is PairValue)
        it
    else
        throw EvaluationException("Wrong Parameter, not list of pairs")
}

fun isRecordDefinition(list: List<Value>) =
    list.isNotEmpty() && list.all { item ->
        item is PairValue && (item.first() is PropertyValue || item.first() is StringValue || item.first() is Identifier)
    }

fun createMapFromListOfPair(content: List<PairValue>, scope: Scope) = buildMap<String, Value> {
    content.forEach {
        if (it.first() is PropertyValue)
            put((it.first() as PropertyValue).identifier, it.last().evaluate(scope))
        else
            put(it.first().toStringValue().strValue, it.last().evaluate(scope))
    }
}
