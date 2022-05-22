package de.gma.gamma.evaluation.string

import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BasicStringTest : BaseEvaluationTest() {
    @Test
    fun `access last element of empty string`() {
        val expr = execute("last \"\"") as StringValue

        Assertions.assertThat(expr.strValue).isEqualTo("")
    }

}
