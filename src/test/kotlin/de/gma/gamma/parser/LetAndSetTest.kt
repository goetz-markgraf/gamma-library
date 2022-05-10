package de.gma.gamma.parser

import de.gma.gamma.datatypes.expressions.LetExpression
import de.gma.gamma.datatypes.expressions.SetExpression
import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class LetAndSetTest : BaseParserTest() {
    @Test
    fun `simple let expression`() {
        val source = "let a = 10"

        val result = getExpression(source)

        assertThat(result).isInstanceOf(LetExpression::class.java)
        val le = result as LetExpression

        assertThat(le.identifier.name).isEqualTo("a")
        assertThat(le.boundValue).isInstanceOf(IntegerValue::class.java)
    }

    @Test
    fun `simple set expression`() {
        val source = "set a! = 10"

        val result = getExpression(source)

        assertThat(result).isInstanceOf(SetExpression::class.java)
        val le = result as SetExpression

        assertThat(le.identifier.name).isEqualTo("a!")
        assertThat(le.boundValue).isInstanceOf(IntegerValue::class.java)
    }

    @Test
    fun `set expression need identifier with ! at end`() {
        val source = "set a = 10"

        assertThatThrownBy {
            getExpression(source)
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Only ids with '!' at end of name can be mutated")
    }

}
