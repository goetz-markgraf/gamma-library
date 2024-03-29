package de.gma.gamma.evaluation.function

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class VarArgsTest : BaseEvaluationTest() {

    @Test
    fun `too many parameters lead to a list as last param`() {
        val result = execute("print 10 20") as ListValue

        assertThat(result.size()).isEqualTo(2)
        assertThat((result.first() as IntegerValue).longValue).isEqualTo(10L)
        assertThat((result.last() as IntegerValue).longValue).isEqualTo(20L)
        assertOutput("10 20\n")
    }

    @Test
    fun `call varargs that must be evaluated first`() {
        val result = execute("print* \"Hello\" (10+20)") as ListValue

        assertThat(result.size()).isEqualTo(2)
        assertThat(result.first().toStringValue().strValue).isEqualTo("Hello")
        assertThat(result.last().toStringValue().strValue).isEqualTo("30")
        assertOutput("Hello 30")
    }

    @Test
    fun `when without the list definition`() {
        val result = execute(
            """
            let a = 10
            when
                (a < 10 -> "smaller")
                (a > 10 -> "greater")
                (else -> "equal")
        """.trimIndent()
        ) as StringValue

        assertThat(result.strValue).isEqualTo("equal")
    }
}
