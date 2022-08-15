package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ModuleScopeTest : BaseEvaluationTest() {

    @Test
    fun `a scope does contain the identifier 'this'`() {
        val result = execute("this") as ModuleScope

        assertThat(result.getAllNames()).hasSize(2)
        assertThat(result.getAllNames()).contains("this", "super")
    }

    @Test
    fun `a scope does contain the identifier 'super'`() {
        val result = execute("super") as ModuleScope

        assertThat(result.getAllNames()).contains("|>", "neg", "first", "..", "reduce", "map")
    }

    @Test
    fun `the identifier this in a function is a new one`() {
        val result = execute("[a : this] () ≠ this") as BooleanValue

        assertThat(result.boolValue).isTrue
    }

    @Test
    fun `in a function there is also a this identifier`() {
        val result = execute("[a : this] ()") as ModuleScope

        assertThat(result.getAllNames()).hasSize(3)

        assertThat(result.getAllNames()).contains("super", "this", "a")
    }
}
