package de.gma.gamma.parser

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.expressions.IfExpression
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ElvisTest : BaseParserTest() {
    @Test
    fun `parse a simple if expression`() {
        val expression = getExpression("a ? 1 : 2.3")

        assertThat(expression).isInstanceOf(IfExpression::class.java)
        val ifExpr = expression as IfExpression

        assertThat(ifExpr.predicate).isInstanceOf(Identifier::class.java)
        assertThat(ifExpr.thenExpr).isInstanceOf(IntegerValue::class.java)
        assertThat(ifExpr.elseExpr).isInstanceOf(FloatValue::class.java)

        assertThat(ifExpr.prettyPrint()).isEqualTo("a ? 1 : 2.3")
    }
}
