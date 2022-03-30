package de.gma.gamma.old.olddatatypes

import de.gma.gamma.parser.Position

open class Value(
    val sourceName: String,
    val start: Position,
    val end: Position
) {
}
