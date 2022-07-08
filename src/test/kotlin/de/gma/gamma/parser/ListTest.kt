package de.gma.gamma.parser

import de.gma.gamma.datatypes.list.ListValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ListTest : BaseParserTest() {
    @Test
    fun `parse an empty list`() {
        val expression = getExpression("{ }")

        assertThat(expression).isInstanceOf(ListValue::class.java)
        val l = expression as ListValue

        assertThat(l.size()).isEqualTo(0)
        assertThat(l.prettyPrint()).isEqualTo("{ }")
    }

    @Test
    fun `parse a list with a single value`() {
        val expression = getExpression("{10}")

        assertThat(expression).isInstanceOf(ListValue::class.java)
        val l = expression as ListValue

        assertThat(l.size()).isEqualTo(1)
        assertThat(l.prettyPrint()).isEqualTo("{10}")
    }

    @Test
    fun `parse a block with a two expressions`() {
        val expression = getExpression("{10,20}") as ListValue

        assertThat(expression).isInstanceOf(ListValue::class.java)

        assertThat(expression.size()).isEqualTo(2)
        assertThat(expression.prettyPrint()).isEqualTo("{10, 20}")
    }

    @Test
    fun `parse complex list with function calls`() {
        val expression = getExpression(
            """
            {
                a > b -> print "groesser"
                a < b -> print "kleiner"
                else -> print "gleich"
            }
        """.trimIndent()
        )

        assertThat(expression).isInstanceOf(ListValue::class.java)

        val list = expression as ListValue
        assertThat(list.size()).isEqualTo(3)
    }

    @Test
    fun `parse complex list with function calls alternative characters`() {
        val expression = getExpression(
            """
            {
                a > b » print "groesser"
                a < b » print "kleiner"
                else » print "gleich"
            }
        """.trimIndent()
        )

        assertThat(expression).isInstanceOf(ListValue::class.java)

        val list = expression as ListValue
        assertThat(list.size()).isEqualTo(3)
    }
}
