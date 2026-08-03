package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.parser.GammaException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class RecursionDepthTest : BaseEvaluationTest() {

    @Test
    fun `deeply nested scope resolves outer binding without stack overflow`() {
        // Bind x in the outermost scope, then call a deeply recursive function
        // that never shadows x, forcing scope-chain lookups at every level.
        val result = execute(
            """
            let x = 42
            let descend n =
                n <= 0
                ? x
                : descend (n - 1)
            descend 100
            """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(42L)
    }

    @Test
    fun `shallow recursion works normally`() {
        val result = execute(
            """
            let sum n =
                n <= 0
                ? 0
                : n + sum (n - 1)
            sum 10
            """.trimIndent()
        ) as IntegerValue

        assertThat(result.longValue).isEqualTo(55L)
    }

    @Test
    fun `infinite recursion throws a friendly GammaException instead of StackOverflowError`() {
        assertThatThrownBy {
            execute(
                """
                let inf n = inf (n + 1)
                inf 0
                """.trimIndent()
            )
        }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("Stack overflow")
    }

    @Test
    fun `a StackOverflowError is not thrown for infinite recursion`() {
        // The JVM must not crash — only a GammaException should escape.
        val threw = try {
            execute(
                """
                let inf n = inf (n + 1)
                inf 0
                """.trimIndent()
            )
            false
        } catch (e: GammaException) {
            true
        } catch (e: StackOverflowError) {
            false  // this is the failure case
        }
        assertThat(threw).isTrue()
    }
}
