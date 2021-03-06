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
            "is-list? {:id}",
            "is-list? \"a\"",
            "is-empty? {}",
            "is-not-empty? {:id}",
            "is-pair? {1, 2}"
        ]
    )
    fun `check that result is true`(code: String) {
        val expr = execute(code) as BooleanValue

        assertThat(expr.boolValue).isTrue
    }


    @ParameterizedTest
    @ValueSource(
        strings = [
            "is-list? :id",
            "is-empty? {:id}",
            "is-not-empty? {}",
            "is-pair? {1, 2, 3}",
            "is-pair? {1}",
            "is-pair? {}",
            "is-pair? :id",
            "is-pair? \"ab\""
        ]
    )
    fun `check that result is false`(code: String) {
        val expr = execute(code) as BooleanValue

        assertThat(expr.boolValue).isFalse
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "{1, 2, 3} |> contains? 2",
            "\"123\" |> contains? \"2\""
        ]
    )
    fun `the given item is in the list`(code: String) {
        val result = execute(code) as BooleanValue

        assertThat(result.boolValue).isTrue
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "{1, 2, 3} |> contains? 4",
            "\"123\" |> contains? \"4\""
        ]
    )
    fun `the given item is not in the list`(code: String) {
        val result = execute(code) as BooleanValue

        assertThat(result.boolValue).isFalse
    }
}
