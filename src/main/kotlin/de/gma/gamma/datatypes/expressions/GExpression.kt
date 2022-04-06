package de.gma.gamma.datatypes.expressions

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.parser.Position

abstract class GExpression(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : GValue(GValueType.EXPRESSION, sourceName, beginPos, endPos)
