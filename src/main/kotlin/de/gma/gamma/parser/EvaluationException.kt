package de.gma.gamma.parser

import de.gma.gamma.builtins.builtInSource

class EvaluationException(
    message: String,
    val source: String = builtInSource,
    val line: Int = 0,
    val col: Int = 0
) : RuntimeException(message) {

    private var added = 0

    fun add(stackTraceElement: StackTraceElement?) {
        val st = stackTrace
        val newSt = arrayOfNulls<StackTraceElement>(st.size + 1)
        System.arraycopy(st, 0, newSt, 0, added)
        newSt[added] = stackTraceElement
        System.arraycopy(st, added, newSt, added + 1, st.size - added)
        stackTrace = newSt
        added++
    }

    fun stackTraceAsString(): String {
        val builder = StringBuilder()
        val trace = stackTrace
        for (i in 0 until added) {
            builder.append("    at ")
            builder.append(trace[i].methodName).append("(")
            builder.append(trace[i].fileName).append(":")
            builder.append(trace[i].lineNumber).append(")")
            builder.append(System.lineSeparator())
        }
        return builder.toString()
    }

}
