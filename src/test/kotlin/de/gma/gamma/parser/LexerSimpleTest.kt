package de.gma.gamma.parser

import de.gma.gamma.Position
import de.gma.gamma.Token
import de.gma.gamma.TokenType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource


class LexerSimpleTest {

    lateinit var lexer: Lexer

    @Test
    fun `parse the empty string`() {
        val token = getTokenFromInput("")

        assertThat(token.type).isEqualTo(TokenType.EOF)
    }


    // ============== whitespace ==============


    @Test
    fun `skip leading spaces`() {
        val source = " a"

        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.ID,
            content = "a",
            start = 1,
            end = 1
        )
        assertThat(token.start).isEqualTo(Position(1, 1, 0))
        assertThat(token.end).isEqualTo(Position(1, 1, 0))
    }

    @Test
    fun `skip comment`() {
        val source = "#comment"
        val token = getTokenFromInput(source)
        assertThat(token.type).isEqualTo(TokenType.EOF)
    }

    @Test
    fun `skip comment with newline`() {
        val source = "#comment\na"
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.ID,
            content = "a",
            start = 9,
            end = 9
        )
        assertThat(token.start).isEqualTo(Position(9, 0, 1))
        assertThat(token.end).isEqualTo(Position(9, 0, 1))
    }


    @Test
    fun `skip leading newline`() {
        val source = "\na"
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.ID,
            content = "a",
            start = 1,
            end = 1
        )

        assertThat(token.start).isEqualTo(Position(1, 0, 1))
        assertThat(token.end).isEqualTo(Position(1, 0, 1))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            ",", ";", ",,", ",;", ";,;,;,;"
        ]
    )
    fun `parse expression end tokens`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.EXEND,
            content = source,
            end = source.length - 1
        )
    }

    // ============== operators ==============


    @ParameterizedTest
    @ValueSource(
        strings = [
            "<", ">", "-", "+", "^", "/", ":", "%", "\\", "$", "|", "=", "!", "&"
        ]
    )
    fun `parse a valid operator character`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = TokenType.OP,
            content = input,
            end = 0
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "<=", ">>", "!-", "++", "!^", "/>", ":>", "%>", "\\-", "$$", "|>", "!=", "!!", "!&"
        ]
    )
    fun `parse 2 character operator`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.OP,
            content = source,
            end = 1
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "´<=´", "`>>`", "´-`", "`+`"
        ]
    )
    fun `parse ticked operator`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.TOP,
            content = source.drop(1).dropLast(1),
            start = 1,
            end = source.length - 2
        )
    }

    @Test
    fun `parse the unit () token`() {
        val source = "()"
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.UNIT,
            content = "()",
            end = 1
        )
    }


    // ============== identifiers ==============

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a",
            "a1",
            "a.a",
            "a1.a1",
            "a?",
            "a!",
            "a*",
            "a",
            "a_a",
            "a-a",
            "a+a"
        ]
    )
    fun `parse correct identifier names`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.ID,
            content = source,
            end = source.length - 1
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a.",
            "a-",
            "a+",
            "a_"
        ]
    )
    fun `parse identifier names with invalid ending characters`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.ID,
            content = source.dropLast(1),
            end = 0
        )

        val nextToken = lexer.nextToken()
        assertThat(nextToken.type).matches { it == TokenType.ERROR || it == TokenType.OP }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "´a6a´",
            "´a´",
            "´a!´",
            "´a6a`",
            "`a´",
            "`a6a`",
            "`a`",
            "`a!`"
        ]
    )
    fun `parse (back-)ticked identifier`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = TokenType.TID,
            content = source.drop(1).dropLast(1),
            start = 1,
            end = source.length - 2
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "´a6a",
            "`a",
            "´a!\"´",
            "´a!<´",
        ]
    )
    fun `parse wrong (back-)ticked identifier`(source: String) {
        val token = getTokenFromInput(source)

        assertThat(token.type).isEqualTo(TokenType.ERROR)
        assertThat(token.content).startsWith(source.drop(1).first().toString())
    }


    // ============== Numbers ==============

    @ParameterizedTest
    @ValueSource(strings = ["10", "-10", "10.5", "-10.5", ".5", "-.5"])
    fun `parse a number`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = TokenType.NUMBER,
            content = input,
            end = input.length - 1
        )
    }


    @Test
    fun `parse a decimal number - do not take more than one dot`() {
        val token = getTokenFromInput("10.5.")

        assertToken(
            token,
            type = TokenType.NUMBER,
            content = "10.5",
            end = 3
        )
        assertToken(
            lexer.nextToken(),
            type = TokenType.ERROR,
            content = ".",
            start = 4,
            end = 4
        )
    }

    // ============== Parens ==============

    @ParameterizedTest
    @ValueSource(strings = ["(", ")", "[", "]", "{", "}"])
    fun `parse a parentheses`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = TokenType.PARENS,
            content = input,
            end = 0
        )
    }


    // ========== String =================


    @ParameterizedTest
    @ValueSource(strings = ["\"\"", "\"a\""])
    fun `parse a string`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = TokenType.STRING,
            content = input.drop(1).dropLast(1),
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
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = TokenType.STRING,
            content = content,
            end = length + 1
        )
    }

    @Test
    fun `parse string with an ESC + RETURN`() {
        val input = "\"a\\\na\""
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = TokenType.STRING,
            content = "aa",
            end = 5
        )
    }


    // ========== Helper Functions =================


    private fun getTokenFromInput(input: String): Token {
        lexer = Lexer(input, "Script")
        return lexer.nextToken()
    }


    private fun assertToken(
        token: Token,
        type: TokenType,
        content: String,
        sourceName: String = "Script",
        start: Int = 0,
        end: Int
    ) {
        assertThat(token.type).isEqualTo(type)
        assertThat(token.content).isEqualTo(content)
        assertThat(token.sourceName).isEqualTo(sourceName)
        assertThat(token.start.pos).isEqualTo(start)
        assertThat(token.end.pos).isEqualTo(end)

    }
}
