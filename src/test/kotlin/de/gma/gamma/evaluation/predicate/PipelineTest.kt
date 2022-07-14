package de.gma.gamma.evaluation.predicate

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PipelineTest : BaseEvaluationTest() {

    @Test
    fun `execute a simple pipeline`() {
        val result = execute(
            """
            pipeline res {
                1 + 1
                res * 10
            }
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(20L)
    }

    @Test
    fun `execute a complex pipeline`() {
        val result = execute(
            """
            pipeline "res"
                {1, 2, 3}
                (map [it → it * 2] res)
                (fold 0 (+) res)
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(12L)
    }
}
