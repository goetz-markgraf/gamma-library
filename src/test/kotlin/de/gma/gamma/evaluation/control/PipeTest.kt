package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PipeTest : BaseEvaluationTest() {

    @Test
    fun `normal map`() {
        val result = execute("{0} |> size") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }

    @Test
    fun `normal map alternate form`() {
        val result = execute("{0} ▷ size") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }
}
