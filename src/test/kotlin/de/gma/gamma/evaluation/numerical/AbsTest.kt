package de.gma.gamma.evaluation.numerical

import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AbsTest : BaseEvaluationTest() {

    @Test
    fun `return the absolute of a positive integer`() {
        val result = execute("abs 2") as IntegerValue
        assertThat(result.longValue).isEqualTo(2L)
    }

    @Test
    fun `return the absolute of a negative integer`() {
        val result = execute("abs -2") as IntegerValue
        assertThat(result.longValue).isEqualTo(2L)
    }

    @Test
    fun `return the absolute of a negative string`() {
        val result = execute("abs \"-2\"") as IntegerValue
        assertThat(result.longValue).isEqualTo(2L)
    }

    @Test
    fun `return the absolute of a positive float`() {
        val result = execute("abs 2.0") as FloatValue
        assertThat(result.doubleValue).isEqualTo(2.0)
    }

    @Test
    fun `return the absolute of a negative float`() {
        val result = execute("abs -2.0") as FloatValue
        assertThat(result.doubleValue).isEqualTo(2.0)
    }

    @Test
    fun `return the absolute of a negative float-string`() {
        val result = execute("abs \"-2.0\"") as FloatValue
        assertThat(result.doubleValue).isEqualTo(2.0)
    }

}
