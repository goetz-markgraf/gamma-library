package de.gma.gamma.parser

import de.gma.gamma.datatypes.GInteger
import de.gma.gamma.datatypes.GString
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.expressions.GLetExpression
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ParserExpressionTest : BaseParserTest() {

    @Nested
    inner class LetExpressions {

        @Test
        fun `parse a let statement with a string`() {
            val source = "let hello = \"Hello\""
            val expression = getExpression(source)

            Assertions.assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression
            Assertions.assertThat(let.identifier.identifier).isEqualTo("hello")
            Assertions.assertThat(let.boundValue.type).isEqualTo(GValueType.STRING)
            Assertions.assertThat((let.boundValue as GString).strValue).isEqualTo("Hello")

            Assertions.assertThat(expression.prettyPrint()).isEqualTo("let hello = \"Hello\"")
        }

        @Test
        fun `parse a let statement with a number`() {
            val source = "let a = 10"
            val expression = getExpression(source)

            Assertions.assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression
            Assertions.assertThat(let.identifier.identifier).isEqualTo("a")
            Assertions.assertThat(let.boundValue.type).isEqualTo(GValueType.INTEGER)
            Assertions.assertThat((let.boundValue as GInteger).intValue).isEqualTo(10L)

            Assertions.assertThat(expression.prettyPrint()).isEqualTo("let a = 10")
        }

        @Test
        fun `parse a let expression for an operator`() {
            val expression = getExpression("let (++) = 10")

            Assertions.assertThat(expression).isInstanceOf(GLetExpression::class.java)
            val let = expression as GLetExpression
            Assertions.assertThat(let.identifier.identifier).isEqualTo("++")
            Assertions.assertThat(let.boundValue.type).isEqualTo(GValueType.INTEGER)
            Assertions.assertThat((let.boundValue as GInteger).intValue).isEqualTo(10L)

            Assertions.assertThat(expression.prettyPrint()).isEqualTo("let (++) = 10")
        }
    }
}
