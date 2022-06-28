package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecursiveListProcessingTest : BaseEvaluationTest() {

    @Test
    fun `calculate a sum using recursion`() {
        val result = execute(
            """
            let sum list =
                is-empty? list
                ? 0
                : list.first + sum list.tail
                
            {1, 2, 3} |> sum
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(6L)
    }
}
