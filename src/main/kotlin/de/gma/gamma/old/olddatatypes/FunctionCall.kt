package de.gma.gamma.old.olddatatypes

import de.gma.gamma.parser.Position

class FunctionCall(
    val elements: List<Value>,
    sourceName: String,
    start: Position,
    end: Position
) : Value(sourceName, start, end) {
}
