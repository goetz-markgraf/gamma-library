package de.gma.gamma.parser

import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.expressions.GOperation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ParserOperationTest : BaseParserTest() {

    @Test
    fun `parse a function in operation position`() {
        val source = "a to: b"
        val expression = getExpression(source)

        Assertions.assertThat(expression).isInstanceOf(GOperation::class.java)
        val op = expression as GOperation
        Assertions.assertThat(op.operator.identifier).isEqualTo("to")
        Assertions.assertThat(op.param1.type).isEqualTo(GValueType.IDENTIFIER)
        Assertions.assertThat(op.param2.type).isEqualTo(GValueType.IDENTIFIER)

        Assertions.assertThat(expression.prettyPrint()).isEqualTo("a to: b")
    }

    @Test
    fun `parse an operation`() {
        val source = "a > 1.5"
        val expression = getExpression(source)

        Assertions.assertThat(expression).isInstanceOf(GOperation::class.java)
        val op = expression as GOperation
        Assertions.assertThat(op.operator.identifier).isEqualTo(">")
        Assertions.assertThat(op.param1.type).isEqualTo(GValueType.IDENTIFIER)
        Assertions.assertThat(op.param2.type).isEqualTo(GValueType.FLOAT)

        Assertions.assertThat(expression.prettyPrint()).isEqualTo("a > 1.5")
    }

    @Test
    fun `parse a sum and a product`() {
        val source = "1 + 2 * 3"
        val expression = getExpression(source)

        Assertions.assertThat(expression).isInstanceOf(GOperation::class.java)
        val op = expression as GOperation
        Assertions.assertThat(op.operator.identifier).isEqualTo("+")
        Assertions.assertThat(op.param1.type).isEqualTo(GValueType.INTEGER)
        Assertions.assertThat(op.param2.type).isEqualTo(GValueType.EXPRESSION)

        val op1 = op.param2 as GOperation
        Assertions.assertThat(op1.operator.identifier).isEqualTo("*")
        Assertions.assertThat(op1.param1.type).isEqualTo(GValueType.INTEGER)
        Assertions.assertThat(op1.param2.type).isEqualTo(GValueType.INTEGER)

        Assertions.assertThat(expression.prettyPrint()).isEqualTo("1 + 2 * 3")
    }

    @Test
    fun `parse a product and a sum`() {
        val source = "1 * 2 + 3"
        val expression = getExpression(source)

        Assertions.assertThat(expression).isInstanceOf(GOperation::class.java)
        val op = expression as GOperation
        Assertions.assertThat(op.operator.identifier).isEqualTo("+")
        Assertions.assertThat(op.param1.type).isEqualTo(GValueType.EXPRESSION)
        Assertions.assertThat(op.param2.type).isEqualTo(GValueType.INTEGER)

        val op1 = op.param1 as GOperation
        Assertions.assertThat(op1.operator.identifier).isEqualTo("*")
        Assertions.assertThat(op1.param1.type).isEqualTo(GValueType.INTEGER)
        Assertions.assertThat(op1.param2.type).isEqualTo(GValueType.INTEGER)

        Assertions.assertThat(expression.prettyPrint()).isEqualTo("1 * 2 + 3")
    }

    @Test
    fun `parse an addition`() {
        val source = "10 + 20"
        val expression = getExpression(source)

        Assertions.assertThat(expression).isInstanceOf(GOperation::class.java)
        val op = expression as GOperation
        Assertions.assertThat(op.operator.identifier).isEqualTo("+")
        Assertions.assertThat(op.param1.type).isEqualTo(GValueType.INTEGER)
        Assertions.assertThat(op.param2.type).isEqualTo(GValueType.INTEGER)

        Assertions.assertThat(expression.prettyPrint()).isEqualTo("10 + 20")
    }

    @Test
    fun `parse an subtraction`() {
        val source = "10 - 20"
        val expression = getExpression(source)

        Assertions.assertThat(expression).isInstanceOf(GOperation::class.java)
        val op = expression as GOperation
        Assertions.assertThat(op.operator.identifier).isEqualTo("-")
        Assertions.assertThat(op.param1.type).isEqualTo(GValueType.INTEGER)
        Assertions.assertThat(op.param2.type).isEqualTo(GValueType.INTEGER)

        Assertions.assertThat(expression.prettyPrint()).isEqualTo("10 - 20")
    }
}
