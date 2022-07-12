package de.gma.gamma.evaluation

import de.gma.gamma.parser.EvaluationException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class EvaluationExceptionTest : BaseEvaluationTest() {

    @Test
    fun `should find error in code`() {
        assertThatThrownBy {
            execute(
                """
                let test a = a + x
                
                test 10
            """.trimIndent()
            )
        }.isInstanceOf(EvaluationException::class.java)
            .matches {
                (it as EvaluationException).stackTraceAsString() ==
                        "    at Function(1:14)(Script:1)\n" +
                        "    at Function(3:1)(Script:3)\n"
            }
    }
}
