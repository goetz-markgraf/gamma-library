package de.gma.gamma.evaluation.shell

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExecuteTest : BaseEvaluationTest() {

    @Test
    fun `run a simple list command`() {
        val result = execute("sh \"ls\"") as ListValue

        assertThat(result.allItems().map { it.toStringValue().strValue }).contains(
            "LEARN_GAMMA.md",
            "README.md",
            "src",
            "target"
        )
    }

    @Test
    fun `run a simple list command with a dollar sign`() {
        val result = execute("$ \"ls -l *.md\"") as ListValue

        assertThat(result.allItems()).allMatch {
            it.toStringValue().strValue.contains("LEARN_GAMMA.md")
                    || it.toStringValue().strValue.contains("README.md")
        }
    }
}
