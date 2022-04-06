package de.gma.gamma.parser

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ParserIndentationTest : BaseParserTest() {

    @Test
    fun `error when indentation wrong`() {
        val source = """
             let a =
            10
        """.trimIndent()
        Assertions.assertThatThrownBy {

            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal indentation of Token 10 must be indented to column 2")
    }

}
