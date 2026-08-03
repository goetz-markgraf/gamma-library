package de.gma.gamma.evaluation.shell

import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShellThreadLeakTest : BaseEvaluationTest() {

    private fun countPoolThreads(): Int =
        Thread.getAllStackTraces().keys
            .count { it.name.startsWith("pool-") }

    @Test
    fun `shell invocations must not leak executor threads`() {
        val threadsBefore = countPoolThreads()

        repeat(5) {
            execute("""$ "echo hi"""")
        }

        // Allow threads a moment to terminate if they were properly shut down
        Thread.sleep(200)

        val threadsAfter = countPoolThreads()

        assertThat(threadsAfter)
            .`as`("thread count should not grow after shell invocations (leaked executor threads)")
            .isLessThanOrEqualTo(threadsBefore)
    }
}
