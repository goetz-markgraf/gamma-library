package de.gma.gamma.parser.complex

import de.gma.gamma.datatypes.expressions.LetExpression
import de.gma.gamma.datatypes.functions.LambdaFunction
import de.gma.gamma.parser.BaseParserTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FunctionsTest : BaseParserTest() {
    @Test
    fun `parse a complex example with two functions and a call`() {
        val source = """
            let add = [a b -> a + b]
            
            add 10 20
        """.trimIndent()

        val expressions = getExpressions(source)

        assertThat(expressions).hasSize(2)

        assertThat(expressions.first().prettyPrint()).isEqualTo(
            """
            let add = [ a b ->
                a + b
            ]
            
        """.trimIndent()
        )

        assertThat(expressions[1].prettyPrint()).isEqualTo("add 10 20")
    }

    @Test
    fun `parse a function with a block`() {
        val source = """
            let add = [ a b ->
                   (
                       print "Hello"
                       a + b
                   )
                ]
        """.trimIndent()

        val expression = getExpression(source) as LetExpression
        assertThat(expression.boundValue).isInstanceOf(LambdaFunction::class.java)
    }
}
