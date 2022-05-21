package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.PropertyValue
import de.gma.gamma.datatypes.values.StringValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecordTest:BaseEvaluationTest() {

    @Test
    fun `property followed by a function call`() {
        val exp = execute(":name to: \"vorname\"")

        assertThat(exp).isInstanceOf(ListValue::class.java)

        val l = exp as ListValue
        assertThat(l.size()).isEqualTo(2)
        assertThat(l.first()).isInstanceOf(PropertyValue::class.java)
        assertThat(l.last()).isInstanceOf(StringValue::class.java)
    }
}
