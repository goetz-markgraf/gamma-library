package de.gma.gamma.evaluation.io

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SplitTest : BaseEvaluationTest() {

    @Test
    fun `split a string with single blank into a list`() {
        val result = execute("split \"a b c\"") as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.first().toStringValue().strValue).isEqualTo("a")
    }

    @Test
    fun `split a string with multiple blanks into a list`() {
        val result = execute("split \"a   b   c\"") as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.first().toStringValue().strValue).isEqualTo("a")
    }

    @Test
    fun `split a string with leading and trailing blanks into a list`() {
        val result = execute("split \" a b c \"") as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.first().toStringValue().strValue).isEqualTo("a")
    }
}
