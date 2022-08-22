package de.gma.gamma.evaluation.control

import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DebugTest : BaseEvaluationTest() {

    @Test
    fun `a debug value evaluates to empty`() {
        assertThat(execute("debug")).isInstanceOf(VoidValue::class.java)
    }
}
