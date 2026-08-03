package de.gma.gamma.evaluation.list

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SortFunctionTest : BaseEvaluationTest() {

    @Test
    fun `sort a list of integers`() {
        val result = execute("sort {3, 1, 2}") as ListValue

        assertThat(result.allItems().map { (it as IntegerValue).longValue })
            .containsExactly(1L, 2L, 3L)
    }

    @Test
    fun `sort a list of non-numeric strings lexicographically`() {
        val result = execute("""sort {"banana", "apple", "cherry"}""") as ListValue

        assertThat(result.allItems().map { (it as StringValue).strValue })
            .containsExactly("apple", "banana", "cherry")
    }

    @Test
    fun `sort a list of mixed-case strings lexicographically`() {
        val result = execute("""sort {"Zebra", "apple", "Mango"}""") as ListValue

        assertThat(result.allItems().map { (it as StringValue).strValue })
            .containsExactly("Mango", "Zebra", "apple")
    }

    @Test
    fun `sort a list of numeric strings by numeric value`() {
        val result = execute("""sort {"3", "1", "2"}""") as ListValue

        assertThat(result.allItems().map { (it as StringValue).strValue })
            .containsExactly("1", "2", "3")
    }

    // sort-desc

    @Test
    fun `sort descending a list of integers`() {
        val result = execute("sort-desc {3, 1, 2}") as ListValue

        assertThat(result.allItems().map { (it as IntegerValue).longValue })
            .containsExactly(3L, 2L, 1L)
    }

    @Test
    fun `sort descending a list of non-numeric strings lexicographically`() {
        val result = execute("""sort-desc {"banana", "apple", "cherry"}""") as ListValue

        assertThat(result.allItems().map { (it as StringValue).strValue })
            .containsExactly("cherry", "banana", "apple")
    }

    @Test
    fun `sort descending a list of numeric strings by numeric value`() {
        val result = execute("""sort-desc {"3", "1", "2"}""") as ListValue

        assertThat(result.allItems().map { (it as StringValue).strValue })
            .containsExactly("3", "2", "1")
    }

    @Test
    fun `sort descending a list of numbers and strings - strings come first, then numbers`() {
        val result = execute("""sort-desc {3, "12", "something"}""") as ListValue

        val items = result.allItems()
        assertThat(items).hasSize(3)
        assertThat((items[0] as StringValue).strValue).isEqualTo("something")
        assertThat((items[1] as StringValue).strValue).isEqualTo("12")
        assertThat((items[2] as IntegerValue).longValue).isEqualTo(3L)
    }

    // sort (mixed)

    @Test
    fun `sort a list of numbers and strings by numeric and lexical value`() {
        val result = execute("""sort {3, "12", "something"}""") as ListValue

        val items = result.allItems()
        assertThat(items).hasSize(3)
        assertThat((items[0] as IntegerValue).longValue).isEqualTo(3L)
        assertThat((items[1] as StringValue).strValue).isEqualTo("12")
        assertThat((items[2] as StringValue).strValue).isEqualTo("something")
    }
}
