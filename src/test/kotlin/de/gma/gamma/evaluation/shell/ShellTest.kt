package de.gma.gamma.evaluation.shell

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class ShellTest : BaseEvaluationTest() {

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

    @Test
    fun `get the current working directory`() {
        val result = execute("CWD") as StringValue

        assertThat(result.strValue).endsWith("gamma-library")
    }

    @Test
    fun `move to the src directory`() {
        val result = execute(
            """
            cd "src"
        """.trimIndent()
        ) as StringValue

        assertThat(result.strValue).endsWith("gamma-library" + File.separator + "src")
    }

    @Test
    fun `move to the src directory and fetch new CWD`() {
        val result = execute(
            """
            cd "src"
            
            CWD
        """.trimIndent()
        ) as StringValue

        assertThat(result.strValue).endsWith("gamma-library" + File.separator + "src")
    }

    @Test
    fun `execute a command from another directory`() {
        val result = execute(
            """
            cd "src"
            $ "ls"
        """.trimIndent()
        ) as ListValue

        assertThat(result.size()).isEqualTo(2)
        assertThat(result.allItems().map { it.toStringValue().strValue }).contains(
            "main",
            "test"
        )
    }
}
