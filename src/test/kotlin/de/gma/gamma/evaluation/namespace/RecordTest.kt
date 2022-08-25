package de.gma.gamma.evaluation.namespace

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.EvaluationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RecordTest : BaseEvaluationTest() {

    @Test
    fun `create a record`() {
        val result = execute("{:a -> 1, :b -> 2}") as RecordValue

        assertThat(result.getValueForName("a").toInteger().longValue).isEqualTo(1L)
        assertThat(result.getValueForName("b").toInteger().longValue).isEqualTo(2L)
    }

    @Test
    fun `create a record with string properties`() {
        val result = execute("{\"a\" → 1, \"b\" → 2}") as RecordValue

        assertThat(result.getValueForName("a").toInteger().longValue).isEqualTo(1L)
        assertThat(result.getValueForName("b").toInteger().longValue).isEqualTo(2L)
    }

    @Test
    fun `create a record with string properties in identifiers`() {
        val result = execute(
            """
            let a = "a"
            let b = "b"
            {a → 1, b → 2}
        """.trimIndent()
        ) as RecordValue

        assertThat(result.getValueForName("a").toInteger().longValue).isEqualTo(1L)
        assertThat(result.getValueForName("b").toInteger().longValue).isEqualTo(2L)
    }

    @Test
    fun `update a record with a dynamic created list`() {
        val result = execute(
            """
                let rec-map = 1 .. 2 ▷ map [pos : join pos "-" 1 → pos]
                {} ▷ copy-with rec-map
            """.trimIndent()
        ) as RecordValue

        assertThat(result.getValueForName("1 - 1").toInteger().longValue).isEqualTo(1L)
        assertThat(result.getValueForName("2 - 1").toInteger().longValue).isEqualTo(2L)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "a,1",
            "b,2"
        ]
    )
    fun `shall read values from record as properties`(name: String, expected: Long) {
        val code = """
            {:a -> 1, :b -> 2}
            |> :$name
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "a,1",
            "b,2"
        ]
    )
    fun `shall read values from record`(name: String, expected: Long) {
        val code = """
            {:a -> 1, :b -> 2}
            |> at "$name"
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "a,1",
            "b,2"
        ]
    )
    fun `shall read values from record in dot notation`(name: String, expected: Long) {
        val code = """
            let r = {:a -> 1, :b -> 2}
            r.$name
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }

    @Test
    fun `create copy from a record with changes`() {
        val code = """
            let orig = {:a -> 1, :b -> 2}
            let changed = orig |> copy-with {:b -> 3, :c -> 4}
            
            {orig, changed}
        """.trimIndent()

        val result = execute(code) as ListValue

        val orig = result.first().evaluate(this.scope).toRecord(scope)
        val changed = result.last().evaluate(this.scope).toRecord(scope)

        assertThat(orig.size()).isEqualTo(2)
        assertThat(changed.size()).isEqualTo(3)

        assertThat(orig.getValueForName("b").toInteger().longValue).isEqualTo(2L)
        assertThat(changed.getValueForName("b").toInteger().longValue).isEqualTo(3L)
        assertThatThrownBy {
            orig.getValueForName("c")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Property c not found in {:a -> 1, :b -> 2}")
        assertThat(changed.getValueForName("c").toInteger().longValue).isEqualTo(4L)
    }

    @Test
    fun `throws error if property is not found`() {
        assertThatThrownBy {
            execute("{:a -> 1} |> :b")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Property b not found in {:a -> 1}")
    }

    @Test
    fun `throws error if property is not found by compound identifier`() {
        assertThatThrownBy {
            execute("let r = {:a -> 1}; r.b")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Property b not found in {:a -> 1}")
    }

    @Test
    fun `list all namespace properties`() {
        val result = execute("{:a → 1, :b → 2} ▷ get-properties") as ListValue

        assertThat(result.size()).isEqualTo(2)
        assertThat(result.allItems()).allMatch {
            (it as StringValue).strValue == "a" || it.strValue == "b"
        }
    }
}
