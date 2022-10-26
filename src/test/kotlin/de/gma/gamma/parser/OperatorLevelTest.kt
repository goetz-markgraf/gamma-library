package de.gma.gamma.parser

import de.gma.gamma.datatypes.expressions.FunctionCall
import de.gma.gamma.datatypes.list.ListValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OperatorLevelTest : BaseParserTest() {

    @Test
    fun `cannot retrieve negative operatorlevel`() {
        val result = isOperatorInLevel("x", -1)

        assertThat(result).isFalse
    }

    @Test
    fun `cannot retrieve operatorlevel too hight`() {
        val result = isOperatorInLevel("x", MAX_OPERATOR_LEVEL + 1)

        assertThat(result).isFalse
    }

    @Test
    fun `pair binds weaker than pipe`() {
        val result = getExpression("a ▷ b → a ◁ b") as ListValue

        assertThat(result.size()).isEqualTo(2)
        assertThat((result.first() as FunctionCall).function.prettyPrint()).isEqualTo("▷")
        assertThat((result.last() as FunctionCall).function.prettyPrint()).isEqualTo("◁")
    }
}
