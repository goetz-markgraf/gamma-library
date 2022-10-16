package de.gma.gamma.evaluation.assertions

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AssertTest : BaseEvaluationTest() {

    @Test
    fun `shall return true`() {
        val result = execute("assert {0 -> 0}") as BooleanValue

        assertThat(result.boolValue).isTrue
    }

    @Test
    fun `shall return false`() {
        val result = execute("assert {0 -> 1}") as BooleanValue

        assertThat(result.boolValue).isFalse
        assertOutput("Assertion Failure : Value 0 is not equal to 1\n")
    }

    @Test
    fun `shall return false with two tests`() {
        val result = execute(
            """
            assert
                (0 -> 0)
                "false"
                (1 -> 0)
        """.trimIndent()
        ) as BooleanValue

        assertThat(result.boolValue).isFalse
        assertOutput("Assertion Failure false: Value 1 is not equal to 0\n")
    }
}
