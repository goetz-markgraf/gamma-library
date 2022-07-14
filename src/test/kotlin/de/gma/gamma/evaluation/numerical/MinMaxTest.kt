package de.gma.gamma.evaluation.numerical

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MinMaxTest : BaseEvaluationTest() {

    @Test
    fun `return the highest integer number`() {
        val result = execute("max 1 \"4\" -2 8 6") as IntegerValue
        assertThat(result.longValue).isEqualTo(8L)
    }

    @Test
    fun `return the highest float number`() {
        val result = execute("max 1.0 \"4.0\" -2.0 8.0 6.0") as FloatValue
        assertThat(result.doubleValue).isEqualTo(8.0)
    }

    @Test
    fun `return the highest mixed number`() {
        val result = execute("max 1.0 \"4\" -2.0 8 6.0") as FloatValue
        assertThat(result.doubleValue).isEqualTo(8.0)
    }

    @Test
    fun `return the smallest integer number`() {
        val result = execute("min 1 \"4\" -2 8 6") as IntegerValue
        assertThat(result.longValue).isEqualTo(-2L)
    }

    @Test
    fun `return the smallest float number`() {
        val result = execute("min 1.0 \"4.0\" -2.0 8.0 6.0") as FloatValue
        assertThat(result.doubleValue).isEqualTo(-2.0)
    }

    @Test
    fun `return the smallest mixed number`() {
        val result = execute("min 1.0 \"4\" -2.0 8 6.0") as FloatValue
        assertThat(result.doubleValue).isEqualTo(-2.0)
    }
}
