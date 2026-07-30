package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppendAllTest : BaseEvaluationTest() {

    // expected: append-all {1, 2} {3, 4} -> {1, 2, 3, 4}
    // i.e. new-items come first, list is appended after

    @Test
    fun `append-all with two SimpleLists`() {
        val result = execute("append-all {1, 2} {3, 4}") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with SimpleList as new-items and SubList as list`() {
        val result = execute("append-all {1, 2} (tail {0, 3, 4})") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with SubList as new-items and SimpleList as list`() {
        val result = execute("append-all (tail {0, 1, 2}) {3, 4}") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with two SubLists`() {
        val result = execute("append-all (tail {0, 1, 2}) (tail {0, 3, 4})") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with ListGenerator as new-items and SimpleList as list`() {
        val result = execute("append-all (list-generator 2 [i : i + 1]) {3, 4}") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with SimpleList as new-items and ListGenerator as list`() {
        val result = execute("append-all {1, 2} (list-generator 2 [i : i + 3])") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with two ListGenerators`() {
        val result = execute("append-all (list-generator 2 [i : i + 1]) (list-generator 2 [i : i + 3])") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with SubList as new-items and ListGenerator as list`() {
        val result = execute("append-all (tail {0, 1, 2}) (list-generator 2 [i : i + 3])") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }

    @Test
    fun `append-all with ListGenerator as new-items and SubList as list`() {
        val result = execute("append-all (list-generator 2 [i : i + 1]) (tail {0, 3, 4})") as ListValue

        assertThat(result.prettyPrint()).isEqualTo("{1, 2, 3, 4}")
    }
}
