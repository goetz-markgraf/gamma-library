package de.gma.gamma.parser

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.expressions.Expression
import de.gma.gamma.datatypes.expressions.FunctionCall
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OperationTest : BaseParserTest() {

    @Test
    fun `parse a function in operation position`() {
        val source = "a to: b"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val op = expression as FunctionCall
        assertThat(op.function).isInstanceOf(Identifier::class.java)
        assertThat(op.params).hasSize(2)
        assertThat(op.op1!!).isInstanceOf(Identifier::class.java)
        assertThat(op.op2!!).isInstanceOf(Identifier::class.java)

        assertThat(expression.prettyPrint()).isEqualTo("a to: b")
    }

    @Test
    fun `parse an operation`() {
        val source = "a > 1.5"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val op = expression as FunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo(">")
        assertThat(op.params).hasSize(2)
        assertThat(op.op1!!).isInstanceOf(Identifier::class.java)
        assertThat(op.op2!!).isInstanceOf(FloatValue::class.java)

        assertThat(expression.prettyPrint()).isEqualTo("a > 1.5")
    }

    @Test
    fun `parse a sum and a product`() {
        val source = "1 + 2 * 3 ^ 4"
        val op = getExpression(source) as FunctionCall

        assertThat(op.function.prettyPrint()).isEqualTo("+")
        assertThat(op.op1!!).isInstanceOf(IntegerValue::class.java)
        assertThat(op.op2!!).isInstanceOf(Expression::class.java)

        val op1 = op.op2!! as FunctionCall
        assertThat(op1.function.prettyPrint()).isEqualTo("*")
        assertThat(op1.op1!!).isInstanceOf(IntegerValue::class.java)
        assertThat(op1.op2!!).isInstanceOf(FunctionCall::class.java)

        assertThat(op.prettyPrint()).isEqualTo("1 + 2 * 3 ^ 4")
    }

    @Test
    fun `parse a product and a sum`() {
        val source = "1 * 2 + 3"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val op = expression as FunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("+")
        assertThat(op.op1!!).isInstanceOf(Expression::class.java)
        assertThat(op.op2!!).isInstanceOf(IntegerValue::class.java)

        val op1 = op.op1!! as FunctionCall
        assertThat(op1.function.prettyPrint()).isEqualTo("*")
        assertThat(op1.op1!!).isInstanceOf(IntegerValue::class.java)
        assertThat(op1.op2!!).isInstanceOf(IntegerValue::class.java)

        assertThat(expression.prettyPrint()).isEqualTo("1 * 2 + 3")
    }

    @Test
    fun `parse an addition`() {
        val source = "10 + 20"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val op = expression as FunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("+")
        assertThat(op.op1!!).isInstanceOf(IntegerValue::class.java)
        assertThat(op.op2!!).isInstanceOf(IntegerValue::class.java)

        assertThat(expression.prettyPrint()).isEqualTo("10 + 20")
    }

    @Test
    fun `parse an subtraction`() {
        val source = "10 - 20"
        val expression = getExpression(source)

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val op = expression as FunctionCall
        assertThat(op.function.prettyPrint()).isEqualTo("-")
        assertThat(op.op1!!).isInstanceOf(IntegerValue::class.java)
        assertThat(op.op2!!).isInstanceOf(IntegerValue::class.java)

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

        assertThat(expression).isInstanceOf(FunctionCall::class.java)

        assertThat(expression!!.prettyPrint()).isEqualTo("1 + 2")
    }

    @Test
    fun `check order sum and op`() {
        val op = getExpression("10 + 20 -> 30 + 40") as FunctionCall

        assertThat(op.op1).isInstanceOf(FunctionCall::class.java)
        assertThat(op.op2).isInstanceOf(FunctionCall::class.java)
    }

    @Test
    fun `parse multiple additions after another`() {
        val expression = getExpression("1 + 2 + 3")

        assertThat(expression).isInstanceOf(FunctionCall::class.java)

        val f = expression as FunctionCall
        assertThat(f.op1).isInstanceOf(FunctionCall::class.java)
        assertThat(f.op2).isInstanceOf(IntegerValue::class.java)
    }

    @Test
    fun `parse multiple equal operations after another`() {
        val expression = getExpression("1 > 2 > 3")

        assertThat(expression).isInstanceOf(FunctionCall::class.java)

        val f = expression as FunctionCall
        assertThat(f.op1).isInstanceOf(FunctionCall::class.java)
        assertThat(f.op2).isInstanceOf(IntegerValue::class.java)
    }
}
