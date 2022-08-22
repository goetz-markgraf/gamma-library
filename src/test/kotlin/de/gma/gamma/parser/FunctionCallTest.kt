package de.gma.gamma.parser

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.expressions.FunctionCall
import de.gma.gamma.datatypes.values.VoidValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FunctionCallTest : BaseParserTest() {
    @Test
    fun `parse a simple function call`() {
        val expression = getExpression("print a")

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val call = expression as FunctionCall
        assertThat(call.function.prettyPrint()).isEqualTo("print")
        assertThat(call.params).hasSize(1)
            .first().isInstanceOf(Identifier::class.java)

        assertThat(call.prettyPrint()).isEqualTo("print a")
    }

    @Test
    fun `parse a function call with empty parameters`() {
        val expression = getExpression("print ()")

        assertThat(expression).isInstanceOf(FunctionCall::class.java)
        val call = expression as FunctionCall
        assertThat(call.function.prettyPrint()).isEqualTo("print")
        assertThat(call.params).hasSize(1)
            .first().isInstanceOf(VoidValue::class.java)

        assertThat(call.prettyPrint()).isEqualTo("print ()")
    }

    @Test
    fun `parse two function calls`() {
        val expression1 = getExpression(
            """
            print a
            print b
        """.trimIndent()
        )

        assertThat(expression1).isInstanceOf(FunctionCall::class.java)

        val expression2 = parser.nextExpression(-1)

        assertThat(expression2).isInstanceOf(FunctionCall::class.java)
    }

    @Test
    fun `parse an expression with a line wrap`() {
        val expression1 = getExpression(
            """
            print a
                  b
        """.trimIndent()
        )

        assertThat(expression1).isInstanceOf(FunctionCall::class.java)
        assertThat(expression1!!.prettyPrint()).isEqualTo("print a b")

        val expression2 = parser.nextExpression(-1)

        assertThat(expression2).isNull()
    }

    @Test
    fun `parse two expressions on one line`() {
        val expression1 = getExpression("print a, print b ")

        assertThat(expression1).isInstanceOf(FunctionCall::class.java)

        val expression2 = parser.nextExpression(-1)

        assertThat(expression2).isInstanceOf(FunctionCall::class.java)
    }

    @Test
    fun `parse a function call with a string as function`() {
        val expression = getExpression("\"hello\" a b")
        assertThat(expression).isInstanceOf(FunctionCall::class.java)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "print a ()",
            "print () a",
            "print () ()"
        ]
    )
    fun `empty mixed with other params is ok`(code: String) {
        val expression = getExpression(code)

        assertThat(expression).isInstanceOf(FunctionCall::class.java)

        assertThat(expression!!.prettyPrint()).isEqualTo(code)
    }
}
