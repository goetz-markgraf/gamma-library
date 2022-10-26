package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PipeTest : BaseEvaluationTest() {

    @Test
    fun `normal pipe`() {
        val result = execute("{0} |> size") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }

    @Test
    fun `normal pipe alternate form`() {
        val result = execute("{0} ▷ size") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }

    @Test
    fun `normal pipe right`() {
        val result = execute("size <| {0}") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }

    @Test
    fun `normal pipe right alternative form`() {
        val result = execute("size ◁ {0}") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }
}
