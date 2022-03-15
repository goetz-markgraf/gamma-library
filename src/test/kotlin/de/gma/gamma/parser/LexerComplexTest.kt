package de.gma.gamma.parser

import de.gma.gamma.parser.TokenType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class LexerComplexTest {

    lateinit var lexer: Lexer

    @Test
    fun `parse multiline tokens`() {
        val source = """
            let a = 10
                + " Hallo"
        """.trimIndent()

        lexer = Lexer(source, "Script")

        assertNextToken(LET, "let", 0, 0, 0)
        assertNextToken(ID, "a", 4, 4, 0)
        assertNextToken(OP, "=", 6, 6, 0)
        assertNextToken(NUMBER, "10", 8, 8, 0)
        assertNextToken(OP, "+", 15, 4, 1)
        assertNextToken(STRING, " Hallo", 17, 6, 1)
        assertNextToken(EOF, "", 25, 14, 1)
        assertNextToken(EOF, "", 25, 14, 1)
    }

    @Test
    fun `parse code with minimum whitespace`() {
        val source = "let add a b=a + b"
        lexer = Lexer(source, "Script")

        assertNextToken(LET, "let", 0, 0, 0)
        assertNextToken(ID, "add", 4, 4, 0)
        assertNextToken(ID, "a", 8, 8, 0)
        assertNextToken(ID, "b", 10, 10, 0)
        assertNextToken(OP, "=", 11, 11, 0)
        assertNextToken(ID, "a", 12, 12, 0)
        assertNextToken(OP, "+", 14, 14, 0)
        assertNextToken(ID, "b", 16, 16, 0)
        assertNextToken(EOF, "", 17, 17, 0)
    }

    private fun assertNextToken(type: TokenType, content: String, sPos: Int, sCol: Int, sLine: Int) {
        val token = lexer.nextToken()
        assertThat(token.type).isEqualTo(type)
        assertThat(token.content).isEqualTo(content)
        assertThat(token.start).isEqualTo(Position(sPos, sCol, sLine))
    }
}
