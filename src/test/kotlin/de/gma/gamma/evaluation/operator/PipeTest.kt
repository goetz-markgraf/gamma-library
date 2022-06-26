package de.gma.gamma.evaluation.operator

import de.gma.gamma.datatypes.list.ListValue
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
        val result = execute("{0} ► size") as IntegerValue

        assertThat(result.longValue).isEqualTo(1L)
    }

    @Test
    fun `map pipe function`() {
        val result = execute("{1, 2, 3} =>> [i -> i + 10]") as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat((result.first() as IntegerValue).longValue).isEqualTo(11L)
        assertThat((result.last() as IntegerValue).longValue).isEqualTo(13L)
    }
}
