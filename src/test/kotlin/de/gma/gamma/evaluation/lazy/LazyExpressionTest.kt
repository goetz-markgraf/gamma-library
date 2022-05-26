package de.gma.gamma.evaluation.lazy

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.BaseParserTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LazyExpressionTest : BaseEvaluationTest() {

    @Test
    fun `prepare an identifier`() {
        val source = """
            let a = 10
            
            let f = [ x ->
                x ]
                
            f a
        """.trimIndent()

        val result = execute(source) as IntegerValue

        assertThat(result.intValue).isEqualTo(10L)
    }

    @Test
    fun `prepare a statement`() {
        val source = """
            let returnSecond a b =
                b
                
            returnSecond (print "should not be displayed") (30 + 40)
        """.trimIndent()

        val result = execute(source) as IntegerValue

        assertThat(result.intValue).isEqualTo(70L)
    }
}
