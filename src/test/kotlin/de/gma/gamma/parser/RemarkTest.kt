package de.gma.gamma.parser

import de.gma.gamma.datatypes.Remark
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RemarkTest : BaseParserTest() {

    @Test
    fun `parse a simple remark`() {
        val expression = getExpression("#remark")

        assertThat(expression).isInstanceOf(Remark::class.java)
        val remark = expression as Remark

        assertThat(remark.strValue).isEqualTo("remark")
        assertThat(remark.prettyPrint()).isEqualTo("# remark")
    }

    @Test
    fun `parse a simple documentation`() {
        val expression = getExpression("'remark'")

        assertThat(expression).isInstanceOf(Remark::class.java)
        val remark = expression as Remark

        assertThat(remark.strValue).isEqualTo("remark")
        assertThat(remark.prettyPrint()).isEqualTo("'remark'")
    }

    @Test
    fun `parse a documentation with escaped apostrophe`() {
        val expression = getExpression("'remark\\'remark'")

        assertThat(expression).isInstanceOf(Remark::class.java)
        val remark = expression as Remark

        assertThat(remark.strValue).isEqualTo("remark'remark")
        assertThat(remark.prettyPrint()).isEqualTo("'remark\\'remark'")
    }

    @Test
    fun `parse a multiline documentation`() {
        val expression = getExpression("'remark\nsecond line'")

        assertThat(expression).isInstanceOf(Remark::class.java)
        val remark = expression as Remark

        assertThat(remark.strValue).isEqualTo("remark\nsecond line")
        assertThat(remark.prettyPrint()).isEqualTo("'remark\nsecond line'")
    }
}
