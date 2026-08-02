package de.gma.gamma.evaluation

import de.gma.gamma.parser.GammaException
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
        }.isInstanceOf(GammaException::class.java)
            .matches {
                val frames = (it as GammaException).stackTrace
                frames.size == 2 &&
                    frames[0].methodName == "Function(1:14)" && frames[0].fileName == "Script" &&
                    frames[1].methodName == "Function(3:1)" && frames[1].fileName == "Script"
            }
    }
}
