package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.PropertyValue
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.parser.EvaluationException

fun extractNumber(value: Value) =
    if (value is IntegerValue || value is FloatValue) value
    else
        if (value is StringValue)
            extractNumberFromString(value)
        else UnitValue.build()

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
    return UnitValue.build()
}

fun checkForListOfPairs(
    list: ListValue,
) = list.allItems().map {
    if (it is ListValue && it.size() == 2)
        it
    else
        throw EvaluationException("Wrong Parameter, not list of pairs")
}

fun createMapFromListOfPair(content: List<ListValue>, scope: Scope) = buildMap<String, Value> {
    content.forEach {
        if (it.size() >= 2) {
            if (it.first() is PropertyValue)
                put((it.first() as PropertyValue).identifier, it.last().evaluate(scope))
            else
                put(it.first().toStringValue().strValue, it.last().evaluate(scope))
        } else {
            throw EvaluationException("cannot create record")
        }
    }
}
