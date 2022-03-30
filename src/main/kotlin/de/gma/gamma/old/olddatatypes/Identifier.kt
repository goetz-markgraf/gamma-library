package de.gma.gamma.old.olddatatypes

import de.gma.gamma.parser.Position

class Identifier(
    val name: String,
    sourceName: String,
    start: Position,
    end: Position
) : Value(sourceName, start, end) {
}
