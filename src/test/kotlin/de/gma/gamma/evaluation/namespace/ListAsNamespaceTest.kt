package de.gma.gamma.evaluation.namespace

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ListAsNamespaceTest : BaseEvaluationTest() {

    @ParameterizedTest
    @CsvSource(
        value = [
            "first,1",
            "last,3",
            "size,3"
        ]
    )
    fun `shall read values from list as namespace`(name: String, expected: Long) {
        val code = "{1, 2, 3} |> at \"$name\""

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            ":first,1",
            ":last,3",
            ":size,3"
        ]
    )
    fun `access list via properties`(prop: String, expected: Long) {
        val code = """
            let list = {1, 2, 3}
            
            $prop list
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "first,1",
            "head,1",
            "last,3",
            "size,3"
        ]
    )
    fun `access list via compound identifiers`(id: String, expected: Long) {
        val code = """
            let list = {1, 2, 3}
            
            list.$id
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }
}
