package de.gma.gamma.parser

import de.gma.gamma.datatypes.GInteger
import de.gma.gamma.datatypes.GUnit
import de.gma.gamma.datatypes.expressions.GBlock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlockTest : BaseParserTest() {
    @Test
    fun `parse an empty block`() {
        val expression = getExpression("( )")

        assertThat(expression).isInstanceOf(GUnit::class.java)
    }

    @Test
    fun `parse a block with a single expression`() {
        val expression = getExpression("(10)")

        assertThat(expression).isInstanceOf(GInteger::class.java)
    }

    @Test
    fun `parse a block with a two expressions`() {
        val expression = getExpression("(10,20)")

        assertThat(expression).isInstanceOf(GBlock::class.java)
        val block = expression as GBlock

        assertThat(block.expressions).hasSize(2)

        assertThat(block.prettyPrint()).isEqualTo(
            """
            (
                10
                20
            )
        """.trimIndent()
        )
    }
}
