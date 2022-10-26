package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.values.PairValue
import de.gma.gamma.datatypes.values.PropertyValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecordTest : BaseEvaluationTest() {

    @Test
    fun `property followed by a function call`() {
        val exp = execute(":name -> \"vorname\"") as PairValue

        assertThat(exp.first()).isInstanceOf(PropertyValue::class.java)
        assertThat(exp.last()).isInstanceOf(StringValue::class.java)
    }
}
