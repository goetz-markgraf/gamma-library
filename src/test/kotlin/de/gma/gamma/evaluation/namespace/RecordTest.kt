package de.gma.gamma.evaluation.namespace

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RecordTest : BaseEvaluationTest() {

    @Test
    fun `create a record`() {
        val result = execute("record {:a -> 1, :b -> 2}") as RecordValue

        assertThat(result.getValue("a").toInteger().intValue).isEqualTo(1L)
        assertThat(result.getValue("b").toInteger().intValue).isEqualTo(2L)
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
            record {:a -> 1, :b -> 2}
            |> :$name
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.intValue).isEqualTo(expected)
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
            record {:a -> 1, :b -> 2}
            |> at "$name"
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.intValue).isEqualTo(expected)
    }

    @Test
    fun `create copy from a record with changes`() {
        val code = """
            let orig = record {:a -> 1, :b -> 2}
            let changed = orig |> copy-with {:b -> 3, :c -> 4}
            
            {orig, changed}
        """.trimIndent()

        val result = execute(code) as ListValue

        val orig = result.first().evaluate(this.scope).toRecord()
        val changed = result.last().evaluate(this.scope).toRecord()

        assertThat(orig.size()).isEqualTo(2)
        assertThat(changed.size()).isEqualTo(3)

        assertThat(orig.getValue("b").toInteger().intValue).isEqualTo(2L)
        assertThat(changed.getValue("b").toInteger().intValue).isEqualTo(3L)
        assertThat(orig.getValue("c")).isInstanceOf(UnitValue::class.java)
        assertThat(changed.getValue("c").toInteger().intValue).isEqualTo(4L)
    }
}
