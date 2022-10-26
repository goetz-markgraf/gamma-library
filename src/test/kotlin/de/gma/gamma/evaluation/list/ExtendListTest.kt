package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.PairValue
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

    @Test
    fun `a string can be converted to a list of characters`() {
        val expr = execute("\"Test\" ▷ make-char-list") as ListValue

        assertThat(expr.allItems()).hasSize(4)
        assertThat(expr.first().toStringValue().strValue).isEqualTo("T")
        assertThat(expr.last().toStringValue().strValue).isEqualTo("t")
    }

    @Test
    fun `convert a 2-item-list to a pair`() {
        val expr = execute("make-pair {1, 2}") as PairValue

        assertThat(expr.first().toInteger().longValue).isEqualTo(1L)
        assertThat(expr.last().toInteger().longValue).isEqualTo(2L)
    }
}
