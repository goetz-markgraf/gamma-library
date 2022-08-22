package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
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

    @Test
    fun `insert to a sublist`() {
        val expr = execute("1 :: (tail {2, 3, 4})") as ListValue

        assertThat(expr.prettyPrint()).isEqualTo("{1, 3, 4}")
    }

    @Test
    fun `append all to a sublist`() {
        val expr = execute("appendAll (drop-last {1,2}) (tail {2, 3, 4})") as ListValue

        assertThat(expr.prettyPrint()).isEqualTo("{1, 3, 4}")
    }

    @Test
    fun `concat two sublists`() {
        val expr = execute("(drop-last {1, 2}) @ (tail {2, 3, 4})") as ListValue

        assertThat(expr.prettyPrint()).isEqualTo("{1, 3, 4}")
    }
}
