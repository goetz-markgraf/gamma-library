package de.gma.gamma.evaluation.io

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReadLineTest : BaseEvaluationTest() {

    @Test
    fun `read the content of a file`() {
        val result = execute("read-lines \"src/test/resources/test-files/read-line.txt\"") as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.first().toStringValue().strValue).isEqualTo("line 1")
    }
}
