package de.gma.gamma.builtin.operator

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AddTest : BaseEvaluationTest() {

    @Test
    fun `add integer values`() {
        val code = "10 + 20"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(30))
    }

    @Test
    fun `add float values`() {
        val code = "10.0 + 20.0"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(30.0))
    }

    @Test
    fun `add int und float values`() {
        val code = "10 + 20.5"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(30.5))
    }

    @Test
    fun `add float und int values`() {
        val code = "10.5 + 20"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(30.5))
    }

    @Test
    fun `add an integer and a string`() {
        val code = "10 + \"20\""
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(30))
    }

    @Test
    fun `add a string and a float`() {
        val code = "\"10\" + 20.5"
        val result = execute(code)
        assertThat(result).isEqualTo(FloatValue.build(30.5))
    }

}
