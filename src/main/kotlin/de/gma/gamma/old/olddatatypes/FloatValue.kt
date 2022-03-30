package de.gma.gamma.old.olddatatypes

import de.gma.gamma.parser.Position

class FloatValue(
    val num: Double,
    sourceName: String,
    start: Position,
    end: Position
) : Value(sourceName, start, end) {
}
