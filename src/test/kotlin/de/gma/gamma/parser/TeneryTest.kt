package de.gma.gamma.parser

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.expressions.IfExpression
import de.gma.gamma.datatypes.values.FloatValue
import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TeneryTest : BaseParserTest() {
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

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a ?",
            "a ? b :",
            " a ?\nb : c",
            " a ? b :\nc"
        ]
    )
    fun `tenery error when no then expression`(source: String) {
        assertThatThrownBy {
            getExpression(source)
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal end of expression")
    }
}
