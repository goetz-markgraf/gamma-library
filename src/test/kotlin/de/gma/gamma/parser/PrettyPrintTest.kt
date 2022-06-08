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
                "do [a -> a]",
                "(+) 1 2",
                "map {1, 2, 3} (+)"
            ]
        )
        fun `wraps expression as parameters in parentheses if needed`(source: String) {
            val expression = getExpression(source)

            assertThat(expression!!.prettyPrint()).isEqualTo(source)
        }
    }

    @Nested
    inner class ListPrettyPrint {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "{1}",
                "{1, 2}",
                "{1, 2, 3}",
                "{ }"
            ]
        )
        fun `a simple list is printed`(code: String) {
            val pretty = execute(code)!!.prettyPrint()

            assertThat(pretty).isEqualTo(code)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "'tail {1, 2, 3}','{2, 3}'",
                "'tail {1, 2}','{2}'",
                "'tail {1}','{ }'",
                "'tail { }','{ }'"
            ]
        )
        fun `sublists are printed like full lists`(code: String, expected: String) {
            val pretty = execute(code)!!.prettyPrint()

            assertThat(pretty).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "'map [i -> i * 2] {1, 2, 3}','{2, 4, 6}'",
                "'map [i -> i * 2] {1}','{2}'",
                "'map [i -> i * 2] { }','{ }'"
            ]
        )
        fun `maps can also be printed`(code: String, expected: String) {
            val pretty = execute(code)!!.prettyPrint()

            assertThat(pretty).isEqualTo(expected)
        }
    }
}
