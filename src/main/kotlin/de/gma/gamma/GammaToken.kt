package de.gma.gamma


data class Position(
    val pos: Int,
    val col: Int,
    val line: Int
)

data class GammaToken(
    val type : GammaTokenType,
    val content: String,
    val start: Position,
    val end: Position
)


enum class GammaTokenType {
    NUMBER,
    ERROR,
    PARENS,
    STRING
}
