package de.gma.gamma.parser.lexer

import de.gma.gamma.parser.Lexer
import de.gma.gamma.parser.Position
import de.gma.gamma.parser.Token
import de.gma.gamma.parser.TokenType
import de.gma.gamma.parser.TokenType.*
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

        assertThat(token.type).isEqualTo(EOF)
    }


    // ============== whitespace ==============

    @Test
    fun `detect illegal tab char`() {
        val source = "\ta"

        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = ERROR,
            content = "\t",
            end = 0
        )
    }

    @Test
    fun `skip leading spaces`() {
        val source = " a"

        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = ID,
            content = "a",
            start = 1,
            end = 1
        )
        assertThat(token.start).isEqualTo(Position(1, 1, 0))
        assertThat(token.end).isEqualTo(Position(1, 1, 0))
    }


    @Test
    fun `skip newline`() {
        val source = "\na"
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = ID,
            content = "a",
            start = 1,
            end = 1
        )

        assertThat(token.start).isEqualTo(Position(1, 0, 1))
        assertThat(token.end).isEqualTo(Position(1, 0, 1))
    }

    @Test
    fun `skip windows newline`() {
        val source = "\r\na"
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = ID,
            content = "a",
            start = 2,
            end = 2
        )

        assertThat(token.start).isEqualTo(Position(2, 0, 1))
        assertThat(token.end).isEqualTo(Position(2, 0, 1))
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
            type = EXEND,
            content = source,
            end = source.length - 1
        )
    }

    // ============== operators ==============


    @Test
    fun `single colon is tenery character`() {
        val token = getTokenFromInput(":")

        assertToken(
            token,
            type = TENERY,
            content = ":",
            end = 0
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "<", ">", "-", "+", "^", "/", "*", "%", "\\", "$", "|", "=", "!", "&",
            "≥", "~", "«", "‰", "¶", "☞", "⊖", "⇉", "¦", "♡",
            "∑"
        ]
    )
    fun `parse a valid operator character`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = OP,
            content = input,
            end = 0
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "<=", ">>", "!-", "++", "!^", "/>", ":>", "%>", "\\-", "$$", "|>", "!=", "!!", "!&",
            "😀"
        ]
    )
    fun `parse 2 character operator`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = OP,
            content = source,
            end = 1
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "(<=)", "(>>)", "(-)", "(+)"
        ]
    )
    fun `parse operator function`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = OP_AS_ID,
            content = source.drop(1).dropLast(1),
            start = 1,
            end = source.length - 2
        )
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
            "(+",
            "(+ ",
            "(-a"
        ]
    )
    fun `parse wrong operator function`(source: String) {
        val token = getTokenFromInput(source)

        assertThat(token.type).isEqualTo(OPEN_PARENS)
    }

    @Test
    fun `parse the empty () token`() {
        val source = "()"
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = EMPTY,
            content = "()",
            end = 1
        )
    }


    // ============== identifiers ==============

    @Test
    fun `parse let keyword`() {
        val token = getTokenFromInput("let")
        assertThat(token.type).isEqualTo(LET)
    }

    @Test
    fun `parse set keyword`() {
        val token = getTokenFromInput("set")
        assertThat(token.type).isEqualTo(SET)
    }

    @Test
    fun `parse module keyword`() {
        val token = getTokenFromInput("module")
        assertThat(token.type).isEqualTo(MODULE)
    }

    @Test
    fun `parse match keyword`() {
        val token = getTokenFromInput("match")
        assertThat(token.type).isEqualTo(MATCH)
    }

    @Test
    fun `parse with keyword`() {
        val token = getTokenFromInput("with")
        assertThat(token.type).isEqualTo(WITH)
    }

    @Test
    fun `parse type keyword`() {
        val token = getTokenFromInput("type")
        assertThat(token.type).isEqualTo(TYPE)
    }

    @Test
    fun `parse true keyword`() {
        val token = getTokenFromInput("true")
        assertThat(token.type).isEqualTo(TRUE)
    }

    @Test
    fun `parse false keyword`() {
        val token = getTokenFromInput("false")
        assertThat(token.type).isEqualTo(FALSE)
    }

    @ParameterizedTest
    @ValueSource(strings = [":a", ":a1"])
    fun `parse correct property`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = PROPERTY,
            content = source.drop(1),
            end = source.length - 1
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a",
            "a1",
            "a?",
            "a!",
            "a*",
            "a",
            "a_a",
            "a-a",
            "a+a",
            "_a",
            "_a?",
            "a-",
            "a+",
            "a++",
            "a_-+",
            "π",
            "µ",
            "ƒ"
        ]
    )
    fun `parse correct identifier names`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = ID,
            content = source,
            end = source.length - 1
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a+.a",
            "a.+a",
            "a.a",
            "a1.a1"
        ]
    )
    fun `identifier stops before the dot`(source: String) {
        val token = getTokenFromInput(source)

        val pos = source.indexOf('.')
        assertToken(
            token,
            type = ID,
            content = source.substring(0, pos),
            end = pos - 1
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "correct?or",
            "valid!and"
        ]
    )
    fun `identifier stops with the questionmark or bang`(source: String) {
        val token = getTokenFromInput(source)

        val pos = source.indexOfAny(charArrayOf('!', '?'))
        assertToken(
            token,
            type = ID,
            content = source.substring(0, pos + 1),
            end = pos
        )
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
            "a6a:",
            "a:",
            "a!:",
            "a6a:",
            "a:",
            "a6a:",
            "a:",
            "a!:"
        ]
    )
    fun `parse identifier in operator position`(source: String) {
        val token = getTokenFromInput(source)

        assertToken(
            token,
            type = ID_AS_OP,
            content = source.dropLast(1),
            start = 0,
            end = source.length - 1
        )
    }

    // ============== Numbers ==============

    @ParameterizedTest
    @ValueSource(strings = ["10", "-10", "10.5", "-10.5", ".5", "-.5"])
    fun `parse a number`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = NUMBER,
            content = input,
            end = input.length - 1
        )
    }


    @Test
    fun `parse a decimal number - do not take more than one dot`() {
        val token = getTokenFromInput("10.5.")

        assertToken(
            token,
            type = NUMBER,
            content = "10.5",
            end = 3
        )
        assertToken(
            lexer.nextToken(),
            type = OP,
            content = ".",
            start = 4,
            end = 4
        )
    }

    // ============== Parens ==============

    @ParameterizedTest
    @ValueSource(strings = ["(", "[", "{"])
    fun `parse an opening parentheses`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = OPEN_PARENS,
            content = input,
            end = 0
        )
    }

    @ParameterizedTest
    @ValueSource(strings = [")", "]", "}"])
    fun `parse a closing parentheses`(input: String) {
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = CLOSE_PARENS,
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
            type = STRING,
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
            type = STRING,
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
            type = STRING,
            content = "aa",
            end = 5
        )
    }


    // ========== Comments =================


    @Test
    fun `parse a remark`() {
        val input = "#remark"
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = REMARK,
            content = "remark",
            end = 7
        )
    }

    @Test
    fun `parse a remark with leading space`() {
        val input = "# remark"
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = REMARK,
            content = "remark",
            end = 8
        )
    }

    @Test
    fun `parse a single line documentation`() {
        val input = "'documentation'"
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = DOCUMENTATION,
            content = "documentation",
            end = 15
        )
    }

    @Test
    fun `parse a multi line documentation`() {
        val input = "'documen\ntation'"
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = DOCUMENTATION,
            content = "documen\ntation",
            end = 16
        )
    }

    @Test
    fun `error if eof in a documentation`() {
        val input = "'documentation"
        val token = getTokenFromInput(input)

        assertToken(
            token,
            type = ERROR,
            content = "documentation",
            end = 15
        )
    }


    // ========== Helper Functions =================


    private fun getTokenFromInput(input: String): Token {
        lexer = Lexer(input)
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
