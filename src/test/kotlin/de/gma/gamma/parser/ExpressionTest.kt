package de.gma.gamma.parser

import de.gma.gamma.datatypes.expressions.LetExpression
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExpressionTest : BaseParserTest() {

    @Nested
    inner class LetExpressions {

        @Test
        fun `parse a let statement with documentation`() {
            val source = "'doc'let a = 10"
            val expression = getExpression(source)

            assertThat(expression).isInstanceOf(LetExpression::class.java)
            val let = expression as LetExpression

            assertThat(let.documentation).isNotNull
            assertThat(let.documentation!!.strValue).isEqualTo("doc")

            assertThat(let.prettyPrint()).isEqualTo(
                """
                'doc'
                let a = 10
            """.trimIndent()
            )
        }

        @Test
        fun `parse a let statement with a string`() {
            val source = "let hello = \"Hello\""
            val expression = getExpression(source)

            assertThat(expression).isInstanceOf(LetExpression::class.java)
            val let = expression as LetExpression
            assertThat(let.identifier.name).isEqualTo("hello")
            assertThat(let.boundValue).isInstanceOf(StringValue::class.java)
            assertThat((let.boundValue as StringValue).strValue).isEqualTo("Hello")

            assertThat(expression.prettyPrint()).isEqualTo("let hello = \"Hello\"")
        }

        @Test
        fun `parse a let statement with a number`() {
            val source = "let a = 10"
            val expression = getExpression(source)

            assertThat(expression).isInstanceOf(LetExpression::class.java)
            val let = expression as LetExpression
            assertThat(let.identifier.name).isEqualTo("a")
            assertThat(let.boundValue).isInstanceOf(IntegerValue::class.java)
            assertThat((let.boundValue as IntegerValue).intValue).isEqualTo(10L)

            assertThat(expression.prettyPrint()).isEqualTo("let a = 10")
        }

        @Test
        fun `parse a let expression for an operator`() {
            val expression = getExpression("let (++) = 10")

            assertThat(expression).isInstanceOf(LetExpression::class.java)
            val let = expression as LetExpression
            assertThat(let.identifier.name).isEqualTo("++")
            assertThat(let.boundValue).isInstanceOf(IntegerValue::class.java)
            assertThat((let.boundValue as IntegerValue).intValue).isEqualTo(10L)

            assertThat(expression.prettyPrint()).isEqualTo("let (++) = 10")
        }
    }
}
