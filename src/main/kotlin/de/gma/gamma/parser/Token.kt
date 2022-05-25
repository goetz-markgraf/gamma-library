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
    OPEN_PARENS,
    CLOSE_PARENS,
    STRING,
    OP,
    SPREAD,
    OP_AS_ID,
    ID,
    ID_AS_OP,
    UNIT,
    EXEND,
    LET,
    SET,
    TYPE,
    TRUE,
    FALSE,
    MODULE,
    MATCH,
    WITH,
    ELVIS,
    REMARK,
    DOCUMENTATION
}
