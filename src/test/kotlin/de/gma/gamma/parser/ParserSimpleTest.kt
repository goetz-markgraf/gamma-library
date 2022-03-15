package de.gma.gamma.parser

import de.gma.gamma.Position
import de.gma.gamma.datatypes.FloatValue
import de.gma.gamma.datatypes.IntegerValue
import de.gma.gamma.datatypes.StringValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ParserSimpleTest {


    // ============ string tests ===============


    @Test
    fun `parse a string`() {
        val source = "\"H\""
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(StringValue::class.java)
        assertThat((value as StringValue).content).isEqualTo("H")
        assertThat(value.start).isEqualTo(Position(0, 0, 0))
        assertThat(value.end).isEqualTo(Position(2, 2, 0))
    }


    // ============ number tests ===============

    @Test
    fun `parse an integer number value`() {
        val source = "10"
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(IntegerValue::class.java)
        assertThat((value as IntegerValue).num).isEqualTo(10)
        assertThat(value.start).isEqualTo(Position(0, 0, 0))
        assertThat(value.end).isEqualTo(Position(1, 1, 0))
    }

    @Test
    fun `parse a float number value`() {
        val source = "1.5"
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(FloatValue::class.java)
        assertThat((value as FloatValue).num).isEqualTo(1.5)
        assertThat(value.start).isEqualTo(Position(0, 0, 0))
        assertThat(value.end).isEqualTo(Position(2, 2, 0))
    }
}
