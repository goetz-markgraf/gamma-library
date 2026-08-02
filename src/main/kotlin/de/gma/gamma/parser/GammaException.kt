package de.gma.gamma.parser

import de.gma.gamma.builtins.builtInSource

class GammaException(
    message: String,
    val source: String = builtInSource,
    val line: Int = 0,
    val col: Int = 0
) : RuntimeException(message) {

    private val gammaFrames = mutableListOf<StackTraceElement>()

    fun add(stackTraceElement: StackTraceElement) {
        gammaFrames.add(stackTraceElement)
        stackTrace = gammaFrames.toTypedArray()
    }

}
