package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.UnitValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.EvaluationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BasicListTest : BaseEvaluationTest() {

    @Test
    fun `access first element of list`() {
        val expr = execute("first {1, 2, 3}")
        assertThat(expr).isInstanceOf(IntegerValue::class.java)

        val i = expr as IntegerValue
        assertThat(i.intValue).isEqualTo(1L)
    }

    @Test
    fun `access last element of list`() {
        val expr = execute("last {1, 2, 3}")
        assertThat(expr).isInstanceOf(IntegerValue::class.java)

        val i = expr as IntegerValue
        assertThat(i.intValue).isEqualTo(3L)
    }

    @Test
    fun `access first element of empty list`() {
        val expr = execute("first {}")

        assertThat(expr).isInstanceOf(UnitValue::class.java)
    }

    @Test
    fun `access last element of empty list`() {
        val expr = execute("last {}")

        assertThat(expr).isInstanceOf(UnitValue::class.java)
    }

    @Test
    fun `access nth element of list`() {
        val expr = execute("at 1 {1, 2, 3}")
        assertThat(expr).isInstanceOf(IntegerValue::class.java)

        val i = expr as IntegerValue
        assertThat(i.intValue).isEqualTo(2L)
    }

    @Test
    fun `get the tail of a list`() {
        val expr = execute("tail { 1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(2)
        assertThat((expr.first() as IntegerValue).intValue).isEqualTo(2L)
    }

    @Test
    fun `get the tail of an empty list`() {
        val expr = execute("tail { }") as ListValue

        assertThat(expr.size()).isEqualTo(0)
    }

    @Test
    fun `drop last element of a list`() {
        val expr = execute("drop-last { 1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(2)
        assertThat((expr.first() as IntegerValue).intValue).isEqualTo(1L)
    }

    @Test
    fun `get a slice from a list`() {
        val expr = execute("slice 1 1 {1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(1)
        assertThat((expr.first() as IntegerValue).intValue).isEqualTo(2L)
    }

    @Test
    fun `returns only up to last element of list`() {
        val expr = execute("slice 1 3 {1, 2, 3}") as ListValue

        assertThat(expr.size()).isEqualTo(2)
        assertThat((expr.first() as IntegerValue).intValue).isEqualTo(2L)
        assertThat((expr.last() as IntegerValue).intValue).isEqualTo(3L)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "slice 3 1 {1, 2, 3}",
            "slice 10 1 {1, 2, 3}",
            "slice -1 1 {1, 2, 3}"
        ]
    )
    fun `return empty list if from it too high or negative`(source: String) {
        val expr = execute(source) as ListValue

        assertThat(expr.size()).isEqualTo(0)
    }

    @Test
    fun `throw exception while accessing nth element of empty list`() {
        assertThatThrownBy {
            execute("at 1 {}")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Index out of bounds: 1 outside empty list")
    }

    @Test
    fun `throw exception while accessing size+1 element of list`() {
        assertThatThrownBy {
            execute("at 2 {1, 2}")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Index out of bounds: 2 outside [0..1]")
    }

}
