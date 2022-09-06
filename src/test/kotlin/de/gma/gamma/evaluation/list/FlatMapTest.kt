package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FlatMapTest : BaseEvaluationTest() {

    @Test
    fun `simple flat-map`() {
        val result = execute(" { 1, 2, 3 } ▷ flat-map [v : repeat v]") as ListValue

        assertThat(result.allItems()).hasSize(6)
        assertThat(result.allItems().map { (it as IntegerValue).longValue }).containsExactly(0L, 0L, 1L, 0L, 1L, 2L)
    }

    @Test
    fun `simple flat-map-star`() {
        val result = execute(" { 4, 5, 6 } ▷ flat-map* [v i : repeat (i + 1)]") as ListValue

        assertThat(result.allItems()).hasSize(6)
        assertThat(result.allItems().map { (it as IntegerValue).longValue }).containsExactly(0L, 0L, 1L, 0L, 1L, 2L)
    }
}
