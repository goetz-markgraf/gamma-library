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
    OP_AS_ID,
    ID,
    VOID,
    EXEND,
    LET,
    SET,
    TYPE,
    TRUE,
    FALSE,
    MODULE,
    MATCH,
    WITH,
    TENERY,
    REMARK,
    DOCUMENTATION
}
