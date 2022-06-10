package de.gma.gamma.builtin.operator

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SubtractTest : BaseEvaluationTest() {

    @Test
    fun `subtract integer values`() {
        val code = "20 - 10"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(10))
    }

    @Test
    fun `subtract float values`() {
        val code = "10.0 - 20.0"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(-10.0))
    }

    @Test
    fun `subtract int and float values`() {
        val code = "20 - 10.5"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(9.5))
    }

    @Test
    fun `subtract float and int values`() {
        val code = "20.5 - 10"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(10.5))
    }

    @Test
    fun `subtract an integer and a string`() {
        val code = "20 - \"10\""
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(10))
    }

    @Test
    fun `subtract a string and a float`() {
        val code = "\"20\" - 10.5"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(9.5))
    }

}
