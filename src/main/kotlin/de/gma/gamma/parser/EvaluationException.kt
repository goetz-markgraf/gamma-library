package de.gma.gamma.parser

import de.gma.gamma.builtins.builtInSource

class EvaluationException(
    message: String,
    val source: String = builtInSource,
    val line: Int = 0,
    val col: Int = 0
) : RuntimeException(message)
// TODO Erweiterung Stacktrace einbauen
