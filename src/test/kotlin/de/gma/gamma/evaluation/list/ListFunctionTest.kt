package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ListFunctionTest : BaseEvaluationTest() {


    @Test
    fun `create a simple list generator`() {
        val code = """
            let f i = i
            
            let gen = list-generator 5 f
            
            print (at 1 gen)
            print (at 2 gen)
            
            size gen
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(5L)
    }

    @Test
    fun `use a simple map lambda`() {
        val code = """
            {1, 2, 3} |> map [i -> i * 2]
        """.trimIndent()

        val result = execute(code) as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.allItems().map { it.prettyPrint() }).containsExactly("2", "4", "6")
    }

    @Test
    fun `use a simple map function`() {
        val code = """
            let f i = i * 2
            
            {1, 2, 3} |> map f
        """.trimIndent()

        val result = execute(code) as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.allItems().map { it.prettyPrint() }).containsExactly("2", "4", "6")
    }

    @Test
    fun `runs a function for every item in a list`() {
        val code = """
            let sum! = 0
            
            {1, 2, 3} |> for-each [item -> set sum! = sum! + item]
            
            sum!
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(6L)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "{1, 2, 3} |> fold 0 [acc i -> acc + i]",
            "{1, 2, 3} |> reduce (+)",
            "repeat 4 |> reduce (+)",
            "1 .. 3 |> reduce (+)"
        ]
    )
    fun `several ways to create and sum a list`(code: String) {
        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(6)
    }

    @Test
    fun `create a descending range`() {
        val result = execute("3 .. 1") as ListValue
        assertThat(result.size()).isEqualTo(3)
        assertThat((result.first() as IntegerValue).longValue).isEqualTo(3L)
        assertThat((result.last() as IntegerValue).longValue).isEqualTo(1L)
    }

    @Test
    fun `create a negative ascending range`() {
        val result = execute("-3 .. -1") as ListValue
        assertThat(result.size()).isEqualTo(3)
        assertThat((result.first() as IntegerValue).longValue).isEqualTo(-3L)
        assertThat((result.last() as IntegerValue).longValue).isEqualTo(-1L)
    }

    @Test
    fun `create a negative descending range`() {
        val result = execute("-1 .. -3") as ListValue
        assertThat(result.size()).isEqualTo(3)
        assertThat((result.first() as IntegerValue).longValue).isEqualTo(-1L)
        assertThat((result.last() as IntegerValue).longValue).isEqualTo(-3L)
    }
}
