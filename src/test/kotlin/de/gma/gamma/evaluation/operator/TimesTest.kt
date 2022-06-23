package de.gma.gamma.evaluation.operator

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimesTest : BaseEvaluationTest() {

    @Test
    fun `multiply integer values`() {
        val code = "10 * 20"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(200))
    }

    @Test
    fun `multiply float values`() {
        val code = "10.0 * 20.0"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(200.0))
    }

    @Test
    fun `multiply int und float values`() {
        val code = "10 * 20.0"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(200.0))
    }

    @Test
    fun `multiply float und int values`() {
        val code = "10.0 * 20"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(200.0))
    }


    @Test
    fun `multiply an integer and a string`() {
        val code = "10 * \"20\""
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(200))
    }

    @Test
    fun `multiply a string and a float`() {
        val code = "\"10\" * 20.5"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(205.0))
    }
    
    @Test
    fun `multiply with the mathematical sign`() {
        val code = "10 × 20"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(200))
    }
}
