package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.expressions.Expression
import de.gma.gamma.parser.CH_NEWLINE

fun prettyPrintList(list: List<Value>) = buildString {
    if (list.isEmpty()) return "{ }"

    val multiline = list.indexOfFirst { it is Expression } >= 0
    val splitChars = if (multiline) "$CH_NEWLINE" else ", "

    append('{').append(if (multiline) CH_NEWLINE else "")
    append(list.joinToString(splitChars) { "${if (multiline) "    " else ""}${it.prettyPrint()}" })
    append(if (multiline) CH_NEWLINE else "")
    append('}')
}
