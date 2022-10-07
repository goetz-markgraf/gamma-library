package de.gma.gamma.evaluation.string

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasicStringTest : BaseEvaluationTest() {
    @Test
    fun `access last element of empty string`() {
        val expr = execute("last \"\"") as StringValue

        assertThat(expr.strValue).isEqualTo("")
    }

    @Test
    fun `calculate the length of the string`() {
        val result = execute("size \"abc\"") as IntegerValue

        assertThat(result.longValue).isEqualTo(3L)
    }

    @Test
    fun `access first character`() {
        val result = execute("first \"abc\"") as StringValue

        assertThat(result.strValue).isEqualTo("a")
    }

    @Test
    fun `access last character`() {
        val result = execute("last \"abc\"") as StringValue

        assertThat(result.strValue).isEqualTo("c")
    }

    @Test
    fun `access the tail of a string`() {
        val result = execute("tail \"abc\"") as StringValue

        assertThat(result.strValue).isEqualTo("bc")
    }

    @Test
    fun `access a slice of the string`() {
        val result = execute("slice 1 2 \"abc\"") as StringValue

        assertThat(result.strValue).isEqualTo("bc")
    }
}
