package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.SubList
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SublistTest : BaseEvaluationTest() {
    @Test
    fun `create a simple sublist`() {
        val expr = execute("tail {1, 2, 3}")

        assertThat(expr).isInstanceOf(SubList::class.java)

        val s = expr as SubList
        assertThat(s.origin.allItems()).hasSize(3)
        assertThat(s.origin.first().prettyPrint()).isEqualTo("1")

        assertThat(s.size()).isEqualTo(2)
        assertThat(s.first().prettyPrint()).isEqualTo("2")
        assertThat(s.last().prettyPrint()).isEqualTo("3")

        assertThat(s.allItems()).hasSize(2)
    }

    @Test
    fun `create a sublist from a sublist`() {
        val expr = execute("tail (tail {1, 2, 3})")

        assertThat(expr).isInstanceOf(SubList::class.java)

        val s = expr as SubList
        assertThat(s.origin.allItems()).hasSize(3)
        assertThat(s.origin.first().prettyPrint()).isEqualTo("1")

        assertThat(s.size()).isEqualTo(1)
        assertThat(s.first().prettyPrint()).isEqualTo("3")
    }
}
