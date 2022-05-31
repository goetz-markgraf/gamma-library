package de.gma.gamma.parser

import de.gma.gamma.datatypes.expressions.Expression
import de.gma.gamma.datatypes.functions.FunctionValue
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class FunctionTest : BaseParserTest() {
    @Test
    fun `parse an empty function`() {
        val expression = getExpression("[ () -> ]")

        assertThat(expression).isInstanceOf(FunctionValue::class.java)
        val func = expression as FunctionValue

        assertThat(func.paramNames).isEmpty()

        assertThat(func.expressions).isEmpty()

        assertThat(expression.prettyPrint()).isEqualTo("[() -> ]")
    }

    @Test
    fun `parse a function with two parameters`() {
        val expression = getExpression("[ a b -> a + b ]")

        assertThat(expression).isInstanceOf(FunctionValue::class.java)
        val func = expression as FunctionValue

        assertThat(func.paramNames).hasSize(2)

        assertThat(func.expressions).hasSize(1)
            .first().isInstanceOf(Expression::class.java)

        assertThat(expression.prettyPrint()).isEqualTo("[a b -> a + b]")
    }

    @Test
    fun `a function without parameters is illegal`() {
        assertThatThrownBy {
            getExpression("[ 10 ]")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Function must have at least one parameter or ()")
    }
}
