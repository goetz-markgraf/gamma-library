package de.gma.gamma.parser

import de.gma.gamma.builtins.builtInSource

class GammaException(
    message: String,
    val source: String = builtInSource,
    val line: Int = 0,
    val col: Int = 0
) : RuntimeException(message) {

    private var added = 0

    fun add(stackTraceElement: StackTraceElement) {
        val st = stackTrace
        val newSt = arrayOfNulls<StackTraceElement>(st.size + 1)
        System.arraycopy(st, 0, newSt, 0, added)
        newSt[added] = stackTraceElement
        System.arraycopy(st, added, newSt, added + 1, st.size - added)
        stackTrace = newSt
        added++
    }

    fun stackTraceAsString() =
        stackTrace.take(added).joinToString(System.lineSeparator()) {
            "    at ${it.methodName}(${it.fileName}:${it.lineNumber})"
        } + System.lineSeparator()

}
