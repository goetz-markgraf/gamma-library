package de.gma.gamma.evaluation.lazy

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LazyListTest : BaseEvaluationTest() {

    @Test
    fun `prepare a list for evaluation`() {
        val source = """
            let evalSecond a b =
                b
            
            evalSecond { print 1 } { 1 + 2 }
        """.trimIndent()

        val result = execute(source) as ListValue

        assertThat(result.size()).isEqualTo(1)
    }
}
