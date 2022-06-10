package de.gma.gamma.evaluation.predicate

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BooleanLogicTest : BaseEvaluationTest() {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "2 > 1 & 1 < 2",
            "2 > 1 | 1 > 2",
            "2 < 1 | 1 < 2",
            "not false",
            "not (1 > 2)"
        ]
    )
    fun `shall return true`(code: String) {
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(true))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "2 < 1 & 1 < 2",
            "2 > 1 & 1 > 2",
            "2 < 1 | 1 > 2",
            "not true",
            "not (1 < 2)"
        ]
    )
    fun `shall return false`(code: String) {
        val result = execute(code)
        Assertions.assertThat(result).isEqualTo(BooleanValue.build(false))
    }

}
