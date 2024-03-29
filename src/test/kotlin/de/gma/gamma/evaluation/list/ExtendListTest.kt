package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.PairValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.EvaluationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ExtendListTest : BaseEvaluationTest() {

    @Test
    fun `adds a new element to a list`() {
        val expr = execute("10 :: {1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(4)
        assertThat(expr.first().toInteger().longValue).isEqualTo(10L)
        assertThat(expr.last().toInteger().longValue).isEqualTo(3L)
    }

    @Test
    fun `appends an element to a list`() {
        val expr = execute("append 10 {1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(4)
        assertThat(expr.first().toInteger().longValue).isEqualTo(1L)
        assertThat(expr.last().toInteger().longValue).isEqualTo(10L)
    }

    @Test
    fun `appends a list to a list`() {
        val expr = execute("appendAll {4, 5, 6} {1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(6)
        assertThat(expr.first().toInteger().longValue).isEqualTo(1L)
        assertThat(expr.last().toInteger().longValue).isEqualTo(6L)
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

    @Test
    fun `a list of more than 2 items cannot be converted to a pair`() {
        assertThatThrownBy {
            execute("make-pair {1, 2, 3}")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("{1, 2, 3} cannot be made into a pair")
    }

    @Test
    fun `a list of fewer than 2 items cannot be converted to a pair`() {
        assertThatThrownBy {
            execute("make-pair {1}")

        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("{1} cannot be made into a pair")
    }
}
