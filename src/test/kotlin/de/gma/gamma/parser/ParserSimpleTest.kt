package de.gma.gamma.parser

import de.gma.gamma.datatypes.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ParserSimpleTest {


    // ============ function call tests ===============

    @Test
    fun `parse a function call`() {
        val source = "a a a"
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(FunctionCall::class.java)
        val call = value as FunctionCall
        assertThat(call.elements).hasSize(3)
            .allMatch { (it as Identifier).name == "a" }
        assertThat(call.end).isEqualTo(Position(4, 4, 0))
    }

    @Test
    fun `parse a function call with a string parameter`() {
        val source = "a \"a\""
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(FunctionCall::class.java)
        val call = value as FunctionCall
        assertThat(call.elements).hasSize(2)
            .last().isInstanceOf(StringValue::class.java)
        assertThat(call.end).isEqualTo(Position(4, 4, 0))
    }

    @Test
    fun `parse a function call with unit`() {
        val source = "a()"
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(FunctionCall::class.java)
        val call = value as FunctionCall
        assertThat(call.elements).hasSize(1)
            .last().isInstanceOf(Identifier::class.java)

        assertThat(call.end).isEqualTo(Position(2, 2, 0))
    }

    // ============ identifier tests ===============

    @Test
    fun `parse an identifier`() {
        val source = "a"
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(Identifier::class.java)
        assertThat((value as Identifier).name).isEqualTo("a")
        assertThat(value.start).isEqualTo(Position(0, 0, 0))
        assertThat(value.end).isEqualTo(Position(0, 0, 0))
    }

    @Test
    fun `parse a ticked operator`() {
        val source = "´|>´"
        val parser = Parser(Lexer(source, "Script"))

        val value = parser.nextExpression(0)

        assertThat(value).isInstanceOf(Identifier::class.java)
        assertThat((value as Identifier).name).isEqualTo("|>")
        assertThat(value.start).isEqualTo(Position(1, 1, 0))
        assertThat(value.end).isEqualTo(Position(2, 2, 0))
    }


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
