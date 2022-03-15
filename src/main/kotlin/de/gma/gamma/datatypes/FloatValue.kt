package de.gma.gamma.datatypes

import de.gma.gamma.parser.Position

class FloatValue(
    val num: Double,
    sourceName: String,
    start: Position,
    end: Position
) : Value(sourceName, start, end) {
}
