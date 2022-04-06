package de.gma.gamma.parser

import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.datatypes.expressions.GIfExpression
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParserElvisTest : BaseParserTest() {
    @Test
    fun `parse a simple if expression`() {
        val expression = getExpression("a ? 1 : 2.3")

        assertThat(expression).isInstanceOf(GIfExpression::class.java)
        val ifExpr = expression as GIfExpression

        assertThat(ifExpr.predicate.type).isEqualTo(GValueType.IDENTIFIER)
        assertThat(ifExpr.thenExpr.type).isEqualTo(GValueType.INTEGER)
        assertThat(ifExpr.elseExpr.type).isEqualTo(GValueType.FLOAT)

        assertThat(ifExpr.prettyPrint()).isEqualTo("a ? 1 : 2.3")
    }
}
