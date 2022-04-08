package de.gma.gamma.parser

import de.gma.gamma.datatypes.GUnit
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.expressions.GFunction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FunctionTest : BaseParserTest() {
    @Test
    fun `parse an empty function`() {
        val expression = getExpression("[ () -> ]")

        assertThat(expression).isInstanceOf(GFunction::class.java)
        val func = expression as GFunction

        assertThat(func.params).hasSize(1)
            .first().isInstanceOf(GUnit::class.java)

        assertThat(func.expressions).isEmpty()

        assertThat(expression.prettyPrint()).isEqualTo(
            """
            [ () ->
            ]
        """.trimIndent()
        )
    }

    @Test
    fun `parse a function with two parameters`() {
        val expression = getExpression("[ a b -> a + b ]")

        assertThat(expression).isInstanceOf(GFunction::class.java)
        val func = expression as GFunction

        assertThat(func.params).hasSize(2)
            .allMatch { it.type == GValueType.IDENTIFIER }

        assertThat(func.expressions).hasSize(1)
            .first().matches { it.type == GValueType.EXPRESSION }

        assertThat(expression.prettyPrint()).isEqualTo(
            """
            [ a b ->
                a + b
            ]
        """.trimIndent()
        )
    }
}
