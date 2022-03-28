package de.gma.gamma.parser


data class Position(
    val pos: Int,
    val col: Int,
    val line: Int
)

data class Token(
    val type: TokenType,
    val content: String,
    val sourceName: String,
    val start: Position,
    val end: Position
)


enum class TokenType {
    PROPERTY,
    EOF,
    ERROR,
    NUMBER,
    PARENS,
    STRING,
    OP,
    SPREAD,
    FUNC_OP,
    ID,
    OP_ID,
    UNIT,
    EXEND,
    LET,
    SET,
    TYPE,
    MODULE
}
