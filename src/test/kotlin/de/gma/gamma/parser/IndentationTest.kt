package de.gma.gamma.parser

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IndentationTest : BaseParserTest() {

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

    @Test
    fun `wrong indentation in block, expects only closing )`() {
        val source = """
            (10
            20)
        """.trimIndent()

        Assertions.assertThatThrownBy {
            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal Token 20")
    }

    @Test
    fun `wrong indentation in block`() {
        val source = """
             (10
            20)
        """.trimIndent()

        Assertions.assertThatThrownBy {
            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal indentation of Token 20 must be indented to column 1")
    }
}
