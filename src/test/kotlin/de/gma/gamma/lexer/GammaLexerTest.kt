package de.gma.gamma.lexer

import de.gma.gamma.GammaToken
import de.gma.gamma.GammaTokenType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource


class GammaLexerTest {

    @Test
    fun `parse the empty string`() {
        val tokens = getTokensFromInput("")

        assertThat(tokens).isEmpty()
    }

    // ============== Numbers ==============

    @ParameterizedTest
    @ValueSource(strings = ["10", "-10", "10.5", "-10.5", ".5", "-.5"])
    fun `parse a number`(input: String) {
        val tokens = getTokensFromInput(input)

        assertThat(tokens).hasSize(1)

        assertToken(tokens.first(),
            type = GammaTokenType.NUMBER,
            content = input,
            start = 0,
            end = input.length-1
        )
    }


    @Test
    fun `parse a decimal number - do not take more than one dot`() {
        val tokens = getTokensFromInput("10.5.")

        assertThat(tokens).hasSize(2)

        assertToken(tokens.first(),
            type = GammaTokenType.NUMBER,
            content = "10.5",
            start = 0,
            end = 3
        )
        assertToken(tokens.last(),
            type = GammaTokenType.ERROR,
            content = ".",
            start = 4,
            end = 4
        )
    }

    // ============== Parens ==============

    @ParameterizedTest
    @ValueSource(strings = ["(",")","[","]","{","}"])
    fun `parse a parentheses`(input: String) {
        val tokens = getTokensFromInput(input)

        assertThat(tokens).hasSize(1)

        assertToken(tokens.first(),
            type = GammaTokenType.PARENS,
            content = input,
            start = 0,
            end = 0
        )
    }


    // ========== String =================


    @ParameterizedTest
    @ValueSource(strings = ["\"\"", "\"a\""])
    fun `parse a string`(input: String) {
        val tokens = getTokensFromInput(input)

        assertThat(tokens).hasSize(1)

        assertToken(tokens.first(),
            type = GammaTokenType.STRING,
            content = input.drop(1).dropLast(1),
            start = 0,
            end = input.length - 1
        )
    }

    @ParameterizedTest
    @CsvSource(
        "\"Hallo\",Hallo,5",
        "\"\\n\",'\n',2",
        "\"\\t\",'\t',2",
        "\"\\a\",a,2",
        "\"\\\\\",'\\',2"
    )
    fun `parse strings with ESC characters`(input: String, content: String, length: Int) {
        val tokens = getTokensFromInput(input)

        assertThat(tokens).hasSize(1)

        assertToken(tokens.first(),
            type = GammaTokenType.STRING,
            content = content,
            start = 0,
            end = length+1
        )
    }

    @Test
    fun `parse string with an ESC + RETURN`() {
        val input = "\"a\\\na\""
        val tokens = getTokensFromInput(input)

        assertThat(tokens).hasSize(1)

        assertToken(tokens.first(),
            type = GammaTokenType.STRING,
            content = "aa",
            start = 0,
            end = 5
        )
    }


    // ========== Helper Functions =================


    private fun getTokensFromInput(input: String): List<GammaToken> {
        val lexer = GammaLexer(input)
        return lexer.tokens()
    }


    private fun assertToken(token: GammaToken, type: GammaTokenType, content: String, start: Int, end: Int) {
        assertThat(token.type).isEqualTo(type)
        assertThat(token.content).isEqualTo(content)
        assertThat(token.start.pos).isEqualTo(start)
        assertThat(token.end.pos).isEqualTo(end)

    }


}
