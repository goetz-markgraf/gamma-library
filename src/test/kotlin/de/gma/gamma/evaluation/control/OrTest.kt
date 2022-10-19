package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrTest : BaseEvaluationTest() {

    @Test
    fun `shall return the first string`() {
        val result = execute(
            """
            or {
              "Hallo"
              0
            }
        """.trimIndent()
        ) as StringValue

        assertThat(result.strValue).isEqualTo("Hallo")
    }

    @Test
    fun `shall return the second string`() {
        val result = execute(
            """
            or {
              ""
              ()
              "Hallo"
              0
            }
        """.trimIndent()
        ) as StringValue

        assertThat(result.strValue).isEqualTo("Hallo")
    }

    @Test
    fun `shall return calculate the result`() {
        val result = execute(
            """
            let a = 10
            let b = 0
                
            or {
              a
              b
            }
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(10L)
    }

    @Test
    fun `shall return nil because no truthy value found`() {
        val result = execute(
            """
            or {
              ""
              {}
              ()
              0
              0.0
            }
        """.trimIndent()
        ) as VoidValue
    }
}
