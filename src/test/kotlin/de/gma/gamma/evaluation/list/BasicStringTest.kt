package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.EvaluationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BasicStringTest : BaseEvaluationTest() {

    @Test
    fun `access first element of string`() {
        val expr = execute("first \"123\"") as StringValue

        assertThat(expr.strValue).isEqualTo("1")
    }

    @Test
    fun `access last element of string`() {
        val expr = execute("last \"123\"") as StringValue

        assertThat(expr.strValue).isEqualTo("3")
    }

    @Test
    fun `access first element of empty string`() {
        val expr = execute("first \"\"") as StringValue

        assertThat(expr.strValue).isEqualTo("")
    }

    @Test
    fun `access last element of empty string`() {
        val expr = execute("last \"\"") as StringValue

        assertThat(expr.strValue).isEqualTo("")
    }

    @Test
    fun `access nth element of string`() {
        val expr = execute("at 1 \"123\"") as StringValue

        assertThat(expr.strValue).isEqualTo("2")
    }

    @Test
    fun `get the tail of string`() {
        val expr = execute("tail \"123\"") as StringValue

        assertThat(expr.strValue).isEqualTo("23")
    }

    @Test
    fun `get the tail of an empty string`() {
        val expr = execute("tail \"\"") as StringValue

        assertThat(expr.strValue).isEqualTo("")
    }

    @Test
    fun `drop last element of string`() {
        val expr = execute("drop-last \"123\"") as StringValue

        assertThat(expr.strValue).isEqualTo("12")
    }

    @Test
    fun `get a slice from a string`() {
        val expr = execute("slice 1 1 \"123\"") as StringValue

        assertThat(expr.size()).isEqualTo(1)
        assertThat(expr.first().strValue).isEqualTo("2")
    }

    @Test
    fun `returns only up to last element of list`() {
        val expr = execute("slice 1 3 \"123\"") as StringValue

        assertThat(expr.size()).isEqualTo(2)
        assertThat(expr.first().strValue).isEqualTo("2")
        assertThat(expr.last().strValue).isEqualTo("3")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "slice 3 1 \"123\"",
            "slice 10 1 \"123\"",
            "slice -1 1 \"123\""
        ]
    )
    fun `return empty list if from it too high or negative`(source: String) {
        val expr = execute(source) as StringValue

        assertThat(expr.size()).isEqualTo(0)
    }

    @Test
    fun `throw exception while accessing nth element of empty string`() {
        assertThatThrownBy {
            execute("at 1 \"\"")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Index out of bounds: 1 outside empty string")
    }

    @Test
    fun `throw exception while accessing size+1 element of string`() {
        assertThatThrownBy {
            execute("at 2 \"12\"")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Index out of bounds: 2 outside [0..1]")
    }

}
