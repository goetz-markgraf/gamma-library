package de.gma.gamma.parser

class EvaluationException(
    message: String,
    val source: String,
    val line: Int,
    val col: Int
) : RuntimeException(message)
// TODO Erweiterung Stacktrace einbauen
