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
    inner class SplitBy {

        @Test
        fun `split a string by comma`() {
            val result = execute("split-by \",\" \"1,2,3\"") as ListValue

            assertThat(result.size()).isEqualTo(3)
            assertThat(result.first().toStringValue().strValue).isEqualTo("1")
            assertThat(result.last().toStringValue().strValue).isEqualTo("3")
        }

        @Test
        fun `split a string by multiple commas`() {
            val result = execute("split-by \",\" \"1,,2,3\"") as ListValue

            assertThat(result.size()).isEqualTo(4)
            assertThat(result.first().toStringValue().strValue).isEqualTo("1")
            assertThat(result.getAt(1).toStringValue().strValue).isEqualTo("")
            assertThat(result.last().toStringValue().strValue).isEqualTo("3")
        }

        @Test
        fun `split a string by multiple characters`() {
            val result = execute("split-by \",;\" \"1,;2;3\"") as ListValue

            assertThat(result.size()).isEqualTo(2)
            assertThat(result.first().toStringValue().strValue).isEqualTo("1")
            assertThat(result.last().toStringValue().strValue).isEqualTo("2;3")
        }
    }

    @Nested
    inner class Join {

        @Test
        fun `join all numbers of a list together as one string`() {
            val result = execute("{1, 2, 3} |> join-by \"-\"") as StringValue

            assertThat(result.strValue).isEqualTo("1-2-3")
        }

        @Test
        fun `join all boolean of a list together as one string`() {
            val result = execute("{1 < 1, 1 < 2} |> join-by \", \"") as StringValue

            assertThat(result.strValue).isEqualTo("false, true")
        }

        @Test
        fun `join all numbers of a list together as one string separated with a blank`() {
            val result = execute("join {1, 2, 3}") as StringValue

            assertThat(result.strValue).isEqualTo("1 2 3")
        }
    }
}
