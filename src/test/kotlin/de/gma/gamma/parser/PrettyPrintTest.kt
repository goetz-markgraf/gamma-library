package de.gma.gamma.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class PrettyPrintTest : BaseParserTest() {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "true",
            "false",
            "10",
            "1.5",
            "-1",
            "-1.0",
            "{ }",
            "()",
            ":prop",
            "\"a\"",
            "\"\""
        ]
    )
    fun `keywords and literals print their source code`(source: String) {
        val exp = getExpression(source)

        assertThat(exp!!.prettyPrint()).isEqualTo(source)
    }

    @Nested
    inner class OperatorPrettyPrint {

        @ParameterizedTest
        @ValueSource(
            strings = [
                "1 + 2 * 3",
                "1 + do it",
                "a & b | c & d",
                "(1 + 2) * 3",
                "3 * (1 + 2)",
                "(a |> b) + 2",
                "(a | b) & (c | d)"
            ]
        )
        fun `keeps parentheses where needed`(source: String) {
            val expression = getExpression(source)

            assertThat(expression!!.prettyPrint()).isEqualTo(source)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "(1 * 2) + 3,1 * 2 + 3",
                "3 + (1 * 2),3 + 1 * 2",
            ]
        )
        fun `removes unnecessary parentheses`(source: String, expected: String) {
            val expression = getExpression(source)

            assertThat(expression!!.prettyPrint()).isEqualTo(expected)
        }
    }

    @Nested
    inner class FunctionCallPrettyPrint {

        @ParameterizedTest
        @ValueSource(
            strings = [
                "do it",
                "do 1 2",
                "do (1 + 2)",
                "do (1 ? 2 : 3) \"Hallo\"",
                "do (one, two)",
                "do [a -> a]"
            ]
        )
        fun `wraps expression as parameters in parentheses if needed`(source: String) {
            val expression = getExpression(source)

            assertThat(expression!!.prettyPrint()).isEqualTo(source)
        }
    }
}
