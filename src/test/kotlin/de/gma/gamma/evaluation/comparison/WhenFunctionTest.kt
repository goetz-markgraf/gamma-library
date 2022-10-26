package de.gma.gamma.evaluation.comparison

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class WhenFunctionTest : BaseEvaluationTest() {

    @ParameterizedTest
    @CsvSource(
        value = [
            "1,kleiner",
            "3,groesser",
            "2,gleich"
        ]
    )
    fun `check a simple when function with a list of pair operations`(input: String, expected: String) {
        val code = """
            let a = $input
            when {
                a > 2 -> "groesser"
                a < 2 -> "kleiner"
                true -> "gleich"
            }
        """.trimIndent()

        val result = execute(code) as StringValue

        assertThat(result.strValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "1,kleiner",
            "3,groesser",
            "2,gleich"
        ]
    )
    fun `check a simple when function with a list of lists`(input: String, expected: String) {
        val code = """
            let a = $input
            when (
                {
                    {a > 2, "groesser"}
                    {a < 2, "kleiner"}
                    {true, "gleich"}
                } ▷ make-pair-list)
        """.trimIndent()

        val result = execute(code) as StringValue

        assertThat(result.strValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "\"a\",String",
            "10,Integer",
            "10.4,Float",
            "{1},List",
            "{1;2},Pair",
            "{1;2;3},List",
            "{},Empty",
            "(),Empty",
            "[a : a],Don't know",
        ]
    )
    fun `check a when-star function with a list of inputs`(input: String, expected: String) {
        val result = execute(
            """            
            when* ($input) {
                is-string? → "String"
                is-integer? → "Integer"
                is-float? → "Float"
                is-empty? → "Empty"
                [v : is-list? v ∧ size v = 2] → "Pair"
                is-list? → "List"
                else → "Don't know"
            }
        """.trimIndent()
        ) as StringValue

        assertThat(result.strValue).isEqualTo(expected)
    }
}
