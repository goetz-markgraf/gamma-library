package de.gma.gamma.evaluation.string

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringInterpolationTest : BaseEvaluationTest() {

    @Test
    fun `add a value to a string`() {
        val expr = execute(
            """
            let a = "du da"
            
            "Hallo, $(a)!"
        """.trimIndent()
        ) as StringValue

        assertThat(expr.strValue).isEqualTo("Hallo, du da!")
    }

    @Test
    fun `add two values to a string`() {
        val expr = execute(
            """
            let a = "du da"
            
            "Hallo, $(a) und $(a)!"
        """.trimIndent()
        ) as StringValue

        assertThat(expr.strValue).isEqualTo("Hallo, du da und du da!")
    }
}
