package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

enum class GValueType {
    EXPRESSION,
    INTEGER,
    FLOAT,
    STRING,
    IDENTIFIER,
    UNIT,
    PROPERTY,
    REMARK,
    FUNCTION,
    LIST
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
