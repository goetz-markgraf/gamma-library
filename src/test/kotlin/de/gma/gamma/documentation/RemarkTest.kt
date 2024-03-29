package de.gma.gamma.documentation

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RemarkTest : BaseEvaluationTest() {

    @Test
    fun `a remark evaluates to empty`() {
        assertThat(execute("#remark")).isNull()
    }

    @Test
    fun `a remark can stay behind an expression`() {
        val result = execute("10 + 20 # computes a sum") as IntegerValue

        assertThat(result.longValue).isEqualTo(30L)
    }

    @Test
    fun `a documentation string can stay behind an expression`() {
        execute("10 + 20 'computes a sum'") as VoidValue
    }

    @Test
    fun `a remark after an expression does not change the result of a bind`() {
        val result = execute(
            """
            let a = 10 + 20 # compute a sum
            
            a
        """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(30L)
    }

    @Test
    fun `a remark behind last expression of function changes the result to empty`() {
        execute(
            """
            let add a b =
                a + b # computes the sum
                
            add 10 20
        """.trimIndent()
        ) as IntegerValue
    }
}
