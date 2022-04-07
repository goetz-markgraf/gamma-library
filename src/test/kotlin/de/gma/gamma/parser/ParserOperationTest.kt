package de.gma.gamma.parser

import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.expressions.GFunctionCall
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParserOperationTest : BaseParserTest() {

    @Test
    fun `parse a function in operation position`() {
        val source = "a to: b"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val op = expression as GFunctionCall
        assertThat(op.function.type).isEqualTo(GValueType.IDENTIFIER)
        assertThat(op.params).hasSize(2)
        assertThat(op.params[0].type).isEqualTo(GValueType.IDENTIFIER)
        assertThat(op.params[1].type).isEqualTo(GValueType.IDENTIFIER)

        assertThat(expression.prettyPrint()).isEqualTo("a to: b")
    }

    @Test
    fun `parse an operation`() {
        val source = "a > 1.5"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val op = expression as GFunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo(">")
        assertThat(op.params).hasSize(2)
        assertThat(op.params[0].type).isEqualTo(GValueType.IDENTIFIER)
        assertThat(op.params[1].type).isEqualTo(GValueType.FLOAT)

        assertThat(expression.prettyPrint()).isEqualTo("a > 1.5")
    }

    @Test
    fun `parse a sum and a product`() {
        val source = "1 + 2 * 3"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val op = expression as GFunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("+")
        assertThat(op.params[0].type).isEqualTo(GValueType.INTEGER)
        assertThat(op.params[1].type).isEqualTo(GValueType.EXPRESSION)

        val op1 = op.params[1] as GFunctionCall
        assertThat(op1.function.prettyPrint()).isEqualTo("*")
        assertThat(op1.params[0].type).isEqualTo(GValueType.INTEGER)
        assertThat(op1.params[1].type).isEqualTo(GValueType.INTEGER)

        assertThat(expression.prettyPrint()).isEqualTo("1 + 2 * 3")
    }

    @Test
    fun `parse a product and a sum`() {
        val source = "1 * 2 + 3"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val op = expression as GFunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("+")
        assertThat(op.params[0].type).isEqualTo(GValueType.EXPRESSION)
        assertThat(op.params[1].type).isEqualTo(GValueType.INTEGER)

        val op1 = op.params[0] as GFunctionCall
        assertThat(op1.function.prettyPrint()).isEqualTo("*")
        assertThat(op1.params[0].type).isEqualTo(GValueType.INTEGER)
        assertThat(op1.params[1].type).isEqualTo(GValueType.INTEGER)

        assertThat(expression.prettyPrint()).isEqualTo("1 * 2 + 3")
    }

    @Test
    fun `parse an addition`() {
        val source = "10 + 20"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val op = expression as GFunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("+")
        assertThat(op.params[0].type).isEqualTo(GValueType.INTEGER)
        assertThat(op.params[1].type).isEqualTo(GValueType.INTEGER)

        assertThat(expression.prettyPrint()).isEqualTo("10 + 20")
    }

    @Test
    fun `parse an subtraction`() {
        val source = "10 - 20"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val op = expression as GFunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("-")
        assertThat(op.params[0].type).isEqualTo(GValueType.INTEGER)
        assertThat(op.params[1].type).isEqualTo(GValueType.INTEGER)

        assertThat(expression.prettyPrint()).isEqualTo("10 - 20")
    }

    @Test
    fun `parse an operation with newlines`() {
        val expression = getExpression(
            """
            1
            +
            2
        """.trimIndent()
        )

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)

        assertThat(expression!!.prettyPrint()).isEqualTo("1 + 2")
    }
}
