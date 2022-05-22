package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ListPredicateTest : BaseEvaluationTest() {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "isList? {:id}",
            "isList? \"a\"",
            "isEmpty? {}",
            "isNotEmpty? {:id}",
            "isPair? {1, 2}",
            "isPair? \"ab\""
        ]
    )
    fun `check that result is true`(code: String) {
        val expr = execute(code) as BooleanValue

        assertThat(expr.boolValue).isTrue
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
            "isList? :id",
            "isEmpty? {:id}",
            "isNotEmpty? {}",
            "isPair? {1, 2, 3}",
            "isPair? {1}",
            "isPair? {}",
            "isPair? :id"
        ]
    )
    fun `check that result is false`(code: String) {
        val expr = execute(code) as BooleanValue

        assertThat(expr.boolValue).isFalse
    }
}
