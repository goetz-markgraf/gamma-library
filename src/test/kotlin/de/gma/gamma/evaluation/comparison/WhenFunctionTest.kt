package de.gma.gamma.evaluation.comparison

import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
    fun `check a simple when function with a list`(input: String, expected: String) {
        val code = """
            let a = $input
            when {
                a > 2, "groesser"
                a < 2, "kleiner"
                true, "gleich"
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
            when {
                {a > 2, "groesser"}
                {a < 2, "kleiner"}
                {true, "gleich"}
             }
        """.trimIndent()

        val result = execute(code) as StringValue

        assertThat(result.strValue).isEqualTo(expected)
    }

    @Test
    fun `when with a default case`() {
        val code = """
            when {
                1 > 2, "groesser"
                "kleiner oder gleich"
             }
        """.trimIndent()

        val result = execute(code) as StringValue

        assertThat(result.strValue).isEqualTo("kleiner oder gleich")
    }
}
