package de.gma.gamma.evaluation.string

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StringPredicateTest : BaseEvaluationTest() {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "is-string? \"str\"",
            "is-string? \"\"",
            "is-empty? \"\"",
            "is-not-empty? \"a\"",
            "is-empty? ()"
        ]
    )
    fun `check that result is true`(code: String) {
        val expr = execute(code) as BooleanValue

        Assertions.assertThat(expr.boolValue).isTrue
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
            "is-string? :id",
            "is-string? ()",
            "is-empty? \"a\"",
            "is-not-empty? \"\""
        ]
    )
    fun `check that result is false`(code: String) {
        val expr = execute(code) as BooleanValue

        Assertions.assertThat(expr.boolValue).isFalse
    }

}
