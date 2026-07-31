package de.gma.gamma.builtins

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.parser.GammaException

fun extractNumber(value: Value) =
    when {
        value is IntegerValue || value is FloatValue -> value
        value is StringValue -> extractNumberFromString(value)
        else -> VoidValue.build()
    }

private fun extractNumberFromString(value: StringValue): Value {
    try {
        return value.toInteger()
    } catch (e: GammaException) {
        // do nothing
    }
    try {
        return value.toFloat()
    } catch (e: GammaException) {
        // do nothing
    }
    return VoidValue.build()
}
