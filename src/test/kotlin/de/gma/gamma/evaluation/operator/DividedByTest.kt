package de.gma.gamma.evaluation.operator

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DividedByTest : BaseEvaluationTest() {

    @Test
    fun `divide integer values`() {
        val code = "20 / 2"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(10))
    }

    @Test
    fun `divide float values`() {
        val code = "10.0 / 2.0"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(5.0))
    }

    @Test
    fun `divide int and int values with rounding`() {
        val code = "20 / 8"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(2))
    }

    @Test
    fun `divide float and int values`() {
        val code = "20.0 / 2"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(10.0))
    }

    @Test
    fun `divide an integer and a string`() {
        val code = "20 / \"2\""
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(10))
    }

    @Test
    fun `divide a string and a float`() {
        val code = "\"20\" / 2.0"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(10.0))
    }

    @Test
    fun `divide with the mathematical sign`() {
        val code = "20 ÷ 2"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(10))
    }

}
