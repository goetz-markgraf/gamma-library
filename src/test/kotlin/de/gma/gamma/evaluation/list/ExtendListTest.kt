package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExtendListTest : BaseEvaluationTest() {

    @Test
    fun `adds a new element to a list`() {
        val expr = execute("10 :: {1, 2, 3}")

        assertThat(expr).isInstanceOf(ListValue::class.java)

        val l = expr as ListValue
        assertThat(l.size()).isEqualTo(4)
        assertThat(l.first().prettyPrint()).isEqualTo("10")
        assertThat(l.last().prettyPrint()).isEqualTo("3")
    }

    @Test
    fun `appends an element to a list`() {
        val expr = execute("append 10 {1, 2, 3}")

        assertThat(expr).isInstanceOf(ListValue::class.java)

        val l = expr as ListValue
        assertThat(l.size()).isEqualTo(4)
        assertThat(l.first().prettyPrint()).isEqualTo("1")
        assertThat(l.last().prettyPrint()).isEqualTo("10")
    }

    @Test
    fun `appends a list to a list`() {
        val expr = execute("appendAll {4, 5, 6} {1, 2, 3}")

        assertThat(expr).isInstanceOf(ListValue::class.java)

        val l = expr as ListValue
        assertThat(l.size()).isEqualTo(6)
        assertThat(l.first().prettyPrint()).isEqualTo("1")
        assertThat(l.last().prettyPrint()).isEqualTo("6")
    }

}
