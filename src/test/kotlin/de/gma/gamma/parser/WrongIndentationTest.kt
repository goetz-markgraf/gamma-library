package de.gma.gamma.parser

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class WrongIndentationTest : BaseParserTest() {

    @Test
    fun `error when indentation wrong in let expression`() {
        val source = """
             let a =
            10
        """.trimIndent()
        assertThatThrownBy {

            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal indentation of Token 10 must be indented to column 2")
    }

    @Test
    fun `error when indentation wrong in set expression`() {
        val source = """
             set a! =
            10
        """.trimIndent()
        assertThatThrownBy {

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

        assertThatThrownBy {
            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal Token 20 but was expecting [)]")
    }

    @Test
    fun `wrong indentation in block`() {
        val source = """
             (10
            20)
        """.trimIndent()

        assertThatThrownBy {
            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal indentation of Token 20 must be indented to column 1")
    }

    @Test
    fun `wrong indentation at start of block`() {
        val source = """
             (
            10 20)
        """.trimIndent()

        assertThatThrownBy {
            getExpression(source)
        }
            .isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal indentation of Token 10 must be indented to column 2")
    }
}
