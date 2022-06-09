package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
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
