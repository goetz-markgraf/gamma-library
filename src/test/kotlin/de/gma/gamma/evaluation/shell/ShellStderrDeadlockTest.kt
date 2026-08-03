package de.gma.gamma.evaluation.shell

import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.io.File
import java.util.concurrent.TimeUnit

class ShellStderrDeadlockTest : BaseEvaluationTest() {

    // A command that writes >64 KB exclusively to stderr (the OS pipe buffer limit).
    // Without consuming stderr, the subprocess blocks on its write, and process.waitFor()
    // blocks forever waiting for the process to exit — a deadlock.
    // The @Timeout ensures the test fails fast instead of hanging the entire build.
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    fun `shell must not deadlock when command produces large stderr output`() {
        // Write a script that outputs >64KB to stderr only, nothing to stdout
        val script = File.createTempFile("gamma-test-stderr", ".sh").also {
            it.writeText("#!/bin/sh\nfor i in $(seq 1 5000); do echo \"error-line-\$i\" >&2; done\n")
            it.setExecutable(true)
            it.deleteOnExit()
        }

        val result = execute("""$ "sh ${script.absolutePath}"""")

        // The command produces no stdout, so the result should be an empty list (exit code 0)
        assertThat(result).isNotNull()
    }
}
