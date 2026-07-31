package de.gma.gamma.evaluation.numerical

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.GammaException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `divide by a non-number throws a GammaException`() {
        assertThatThrownBy { execute("2 / true") }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("/ can only be called with two number values")
    }

    @Test
    fun `divide a non-number throws a GammaException`() {
        assertThatThrownBy { execute("true / 2") }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("/ can only be called with two number values")
    }

    @Test
    fun `divide by zero throws a GammaException, not an ArithmeticException`() {
        assertThatThrownBy { execute("10 / 0") }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("Division by zero")
    }

    @Test
    fun `divide float by zero throws a GammaException`() {
        assertThatThrownBy { execute("10.0 / 0") }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("Division by zero")
    }

}
