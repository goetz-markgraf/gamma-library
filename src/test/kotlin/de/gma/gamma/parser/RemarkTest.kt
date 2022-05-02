package de.gma.gamma.parser

import de.gma.gamma.datatypes.direct.GRemark
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RemarkTest : BaseParserTest() {
    @Test
    fun `parse a single remark`() {
        val expression = getExpression("#remark")

        assertThat(expression).isInstanceOf(GRemark::class.java)
        val remark = expression as GRemark

        assertThat(remark.strValue).isEqualTo("remark")
        assertThat(remark.prettyPrint()).isEqualTo("# remark")
    }

    @Test
    fun `parse a single documentation`() {
        val expression = getExpression("'remark'")

        assertThat(expression).isInstanceOf(GRemark::class.java)
        val remark = expression as GRemark

        assertThat(remark.strValue).isEqualTo("remark")
        assertThat(remark.prettyPrint()).isEqualTo("'remark'")
    }
}
