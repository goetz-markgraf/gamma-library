package de.gma.gamma.olddatatypes

import de.gma.gamma.parser.Position

class IntegerValue(
    val num: Int,
    sourceName: String,
    start: Position,
    end: Position
) : Value(sourceName, start, end) {
}
