package de.gma.gamma.evaluation.io

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SplitJoinTest : BaseEvaluationTest() {

    @Nested
    inner class Split {
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

    @Nested
    inner class Join {

        @Test
        fun `join all numbers of a list together as one string`() {
            val result = execute("{1, 2, 3} |> join \", \"") as StringValue

            assertThat(result.strValue).isEqualTo("1, 2, 3")
        }

        @Test
        fun `join all boolean of a list together as one string`() {
            val result = execute("{1 < 1, 1 < 2} |> join \", \"") as StringValue

            assertThat(result.strValue).isEqualTo("false, true")
        }
    }
}
