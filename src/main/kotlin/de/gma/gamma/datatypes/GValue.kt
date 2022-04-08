package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

enum class GValueType {
    INTEGER,
    FLOAT,
    EXPRESSION,
    IDENTIFIER,
    STRING,
    UNIT,
    PROPERTY,
    REMARK
}

abstract class GValue(
    val type: GValueType,
    val sourceName: String,
    val beginPos: Position,
    val endPos: Position
) {
    abstract fun prettyPrint(): String

    override fun toString() = prettyPrint()
}
