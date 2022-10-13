package de.gma.gamma.evaluation.shell

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EnvironmentTest : BaseEvaluationTest() {

    @Test
    fun `get the value of the PATH variable`() {
        val result = execute("env.PATH") as StringValue

        assertThat(result.strValue.trim()).isNotBlank
    }

    @Test
    fun `get the value of the PATH variable with an accessor function`() {
        val result = execute(":PATH env") as StringValue

        assertThat(result.strValue.trim()).isNotBlank
    }

    @Test
    fun `get the value of the PATH variable with the at function`() {
        val result = execute("at \"PATH\" env") as StringValue

        assertThat(result.strValue.trim()).isNotBlank
    }

    @Test
    fun `get the system property for user home`() {
        val result = execute("system.user-home") as StringValue

        assertThat(result.strValue.trim()).isNotBlank
    }
}
