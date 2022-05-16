package de.gma.gamma.builtin.operator

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ComparisonTest : BaseEvaluationTest() {

    @Test
    fun `compare integer values`() {
        val code = "10 > 20"
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(false))
    }

    @Test
    fun `compare float values`() {
        val code = "10.0 >= 20.0"
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(false))
    }

    @Test
    fun `compare int und float values`() {
        val code = "10 < 20.5"
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(true))
    }

    @Test
    fun `compare float und int values`() {
        val code = "10.5 <= 20"
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(true))
    }

    @Test
    fun `compare less that or equal `() {
        val code = "2 <= 2"
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(true))
    }

    @Test
    fun `compare less than not equal `() {
        val code = "2 < 2"
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(false))
    }

}
