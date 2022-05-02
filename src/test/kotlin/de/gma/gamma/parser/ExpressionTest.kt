package de.gma.gamma.parser

import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.direct.GInteger
import de.gma.gamma.datatypes.direct.GString
import de.gma.gamma.datatypes.expressions.GLetExpression
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

            assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression

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

            assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression
            assertThat(let.identifier.name).isEqualTo("hello")
            assertThat(let.boundValue.type).isEqualTo(GValueType.STRING)
            assertThat((let.boundValue as GString).strValue).isEqualTo("Hello")

            assertThat(expression.prettyPrint()).isEqualTo("let hello = \"Hello\"\n")
        }

        @Test
        fun `parse a let statement with a number`() {
            val source = "let a = 10"
            val expression = getExpression(source)

            assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression
            assertThat(let.identifier.name).isEqualTo("a")
            assertThat(let.boundValue.type).isEqualTo(GValueType.INTEGER)
            assertThat((let.boundValue as GInteger).intValue).isEqualTo(10L)

            assertThat(expression.prettyPrint()).isEqualTo("let a = 10\n")
        }

        @Test
        fun `parse a let expression for an operator`() {
            val expression = getExpression("let (++) = 10")

            assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression
            assertThat(let.identifier.name).isEqualTo("++")
            assertThat(let.boundValue.type).isEqualTo(GValueType.INTEGER)
            assertThat((let.boundValue as GInteger).intValue).isEqualTo(10L)

            assertThat(expression.prettyPrint()).isEqualTo("let (++) = 10\n")
        }
    }
}
