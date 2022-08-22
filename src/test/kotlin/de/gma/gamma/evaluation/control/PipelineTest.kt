package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PipelineTest : BaseEvaluationTest() {

    @Test
    fun `execute a simple pipeline`() {
        val result = execute(
            """
            pipeline _ {
                1 + 1
                _ * 10
            }
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(20L)
    }

    @Test
    fun `execute a complex pipeline`() {
        val result = execute(
            """
            pipeline "_"
                {1, 2, 3}
                (map [it : it * 2] _)
                (fold 0 (+) _)
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(12L)
    }
}
