package de.gma.gamma.evaluation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BuiltinPrettyPrintTest : BaseEvaluationTest() {

    @ParameterizedTest(name = "{0} -> [builtin: {1}]")
    @CsvSource(
        value = [
            "(+),+",
            "(-), -",
            "(*), *",
            "(/), /",
            "(>), >",
            "(>=),>=",
            "(<), <",
            "(<=),<=",
            "(=), =",
            "(!=),!=",
            "map, map",
            "filter, filter",
            "fold, fold",
            "reduce, reduce",
            "size, size",
            "first, first",
            "last, last",
            "tail, tail",
            "(..),range",
            "(&), and",
            "(|), or",
            "not, not",
            "abs, abs",
        ]
    )
    fun `builtin functions include their name in prettyPrint`(code: String, expectedName: String) {
        val result = execute(code)!!

        assertThat(result.prettyPrint()).isEqualTo("[builtin: $expectedName]")
    }

    @Nested
    inner class UnicodeAlternatives {

        @ParameterizedTest(name = "{0} is the same builtin as {1}")
        @CsvSource(
            value = [
                "(×), (*)",
                "(÷), (/)",
                "(≠), (!=)",
                "(≥), (>=)",
                "(≤), (<=)",
                "(∧), (&)",
                "(∨), (|)",
                "(▷), (|>)",
            ]
        )
        fun `unicode operators resolve to the same builtin as their ascii counterpart`(unicode: String, ascii: String) {
            val unicodeResult = execute(unicode)!!
            val asciiResult = execute(ascii)!!

            assertThat(unicodeResult.prettyPrint()).isEqualTo(asciiResult.prettyPrint())
        }
    }
}
