package de.gma.gamma.builtins

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.parser.GammaException

fun extractNumber(v: Value): Value =
    when (v) {
        is IntegerValue -> v
        is FloatValue -> v
        is StringValue -> v.strValue.toLongOrNull()?.let { IntegerValue.build(it) }
            ?: v.strValue.toDoubleOrNull()?.let { FloatValue.build(it) }
            ?: VoidValue.build()
        else -> VoidValue.build()
    }
