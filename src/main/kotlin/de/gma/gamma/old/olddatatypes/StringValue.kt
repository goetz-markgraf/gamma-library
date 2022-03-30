package de.gma.gamma.old.olddatatypes

import de.gma.gamma.parser.Position

class StringValue(
    val content: String,
    sourceName: String,
    start: Position,
    end: Position
) : Value(sourceName, start, end) {
}
