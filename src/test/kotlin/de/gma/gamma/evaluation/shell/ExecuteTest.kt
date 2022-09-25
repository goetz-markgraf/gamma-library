package de.gma.gamma.evaluation.shell

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExecuteTest : BaseEvaluationTest() {

    @Test
    fun `run a simple list command`() {
        val result = execute("sh \"ls\"") as StringValue

        assertThat(result.strValue).contains("LEARN_GAMMA.md", "README.md", "src", "target")
    }
}
