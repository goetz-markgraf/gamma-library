package de.gma.gamma.parser

import de.gma.gamma.datatypes.expressions.Block
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.UnitValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlockTest : BaseParserTest() {
    @Test
    fun `parse an empty block`() {
        val expression = getExpression("( )")

        assertThat(expression).isInstanceOf(UnitValue::class.java)
    }

    @Test
    fun `parse a block with a single expression`() {
        val expression = getExpression("(10)")

        assertThat(expression).isInstanceOf(IntegerValue::class.java)
    }

    @Test
    fun `parse a block with a two expressions`() {
        val expression = getExpression("(10,20)")

        assertThat(expression).isInstanceOf(Block::class.java)
        val block = expression as Block

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
