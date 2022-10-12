package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CompositionTest : BaseEvaluationTest() {

    @Test
    fun `combines two functions`() {
        val expr = execute(
            """
            let f = map [i : i + 1]
            let g = map [i : i * 2]
            
            {1, 2, 3} ▷ f ** g
        """.trimIndent()
        ) as ListValue

        assertThat(expr.size()).isEqualTo(3)
        assertThat(expr.first().toInteger().longValue).isEqualTo(4L)
        assertThat(expr.last().toInteger().longValue).isEqualTo(8L)
    }
}
