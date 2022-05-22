package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OperatorLevelsTest : BaseEvaluationTest() {
    @Test
    fun `multiplication before addition`() {
        val expression = execute("1 + 2 * 3")

        assertThat(expression).isInstanceOf(IntegerValue::class.java)
        assertThat(expression?.prettyPrint()).isEqualTo("7")
    }
}
