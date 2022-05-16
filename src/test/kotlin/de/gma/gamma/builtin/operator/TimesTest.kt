package de.gma.gamma.builtin.operator

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimesTest : BaseEvaluationTest() {
    @Test
    fun `multiply integer values`() {
        val code = "10 * 20"
        val result = execute(code)
        assertThat(result).isEqualTo(IntegerValue.build(200))
    }
}
