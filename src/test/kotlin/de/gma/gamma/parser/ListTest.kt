package de.gma.gamma.parser

import de.gma.gamma.datatypes.GList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ListTest : BaseParserTest() {
    @Test
    fun `parse an empty list`() {
        val expression = getExpression("{ }")

        assertThat(expression).isInstanceOf(GList::class.java)
        val l = expression as GList

        assertThat(l.items).hasSize(0)
        assertThat(l.prettyPrint()).isEqualTo("{  }")
    }

    @Test
    fun `parse a list with a single value`() {
        val expression = getExpression("{10}")

        assertThat(expression).isInstanceOf(GList::class.java)
        val l = expression as GList

        assertThat(l.items).hasSize(1)
        assertThat(l.prettyPrint()).isEqualTo("{ 10 }")
    }

    @Test
    fun `parse a block with a two expressions`() {
        val expression = getExpression("{10,20}")

        assertThat(expression).isInstanceOf(GList::class.java)
        val l = expression as GList

        assertThat(l.items).hasSize(2)
        assertThat(l.prettyPrint()).isEqualTo("{ 10, 20 }")
    }
}
