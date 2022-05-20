package de.gma.gamma.datatypes.list

import de.gma.gamma.datatypes.Value
import de.gma.gamma.parser.Position

abstract class AbstractListValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : Value(sourceName, beginPos, endPos) {

    abstract fun first() : Value

    abstract fun last() : Value

    abstract fun size() : Int

}
