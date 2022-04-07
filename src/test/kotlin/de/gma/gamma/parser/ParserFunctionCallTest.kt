package de.gma.gamma.parser

import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.expressions.GFunctionCall
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParserFunctionCallTest : BaseParserTest() {
    @Test
    fun `parse a simple function call`() {
        val expression = getExpression("print a")

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val call = expression as GFunctionCall
        assertThat(call.function.prettyPrint()).isEqualTo("print")
        assertThat(call.params).hasSize(1)
            .first().matches { it.type == GValueType.IDENTIFIER }

        assertThat(call.prettyPrint()).isEqualTo("print a")
    }

    @Test
    fun `parse a function call with unit parameters`() {
        val expression = getExpression("print ()")

        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
        val call = expression as GFunctionCall
        assertThat(call.function.prettyPrint()).isEqualTo("print")
        assertThat(call.params).hasSize(1)
            .first().matches { it.type == GValueType.UNIT }

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

        assertThat(expression1).isInstanceOf(GFunctionCall::class.java)

        val expression2 = parser.nextExpression(-1)

        assertThat(expression2).isInstanceOf(GFunctionCall::class.java)
    }

    @Test
    fun `parse an expression with a line wrap`() {
        val expression1 = getExpression(
            """
            print a
                  b
        """.trimIndent()
        )

        assertThat(expression1).isInstanceOf(GFunctionCall::class.java)
        assertThat(expression1!!.prettyPrint()).isEqualTo("print a b")

        val expression2 = parser.nextExpression(-1)

        assertThat(expression2).isNull()
    }

    @Test
    fun `parse two expressions on one line`() {
        val expression1 = getExpression("print a, print b ")

        assertThat(expression1).isInstanceOf(GFunctionCall::class.java)

        val expression2 = parser.nextExpression(-1)

        assertThat(expression2).isInstanceOf(GFunctionCall::class.java)
    }

    @Test
    fun `parse a function call with a string as function`() {
        val expression = getExpression("\"hello\" a b")
        assertThat(expression).isInstanceOf(GFunctionCall::class.java)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "print a ()",
            "print () a",
            "print () ()"
        ]
    )
    fun `unit with other params leads to error`(code: String) {
        assertThatThrownBy {
            getExpression(code)
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Unit parameter () can only be used as a single parameter")
    }
}
