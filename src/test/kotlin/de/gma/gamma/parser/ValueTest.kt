package de.gma.gamma.parser

import de.gma.gamma.datatypes.Identifier
import de.gma.gamma.datatypes.values.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValueTest : BaseParserTest() {

    @Nested
    inner class PropertiesTest {

        @Test
        fun `parse a property value`() {
            val expression = getExpression(":hallo")

            assertThat(expression).isInstanceOf(PropertyValue::class.java)
            val prop = expression as PropertyValue
            assertThat(prop.identifier).isEqualTo("hallo")

            assertThat(prop.prettyPrint()).isEqualTo(":hallo")
        }
    }

    @Nested
    inner class NumbersTest {
        @Test
        fun `parse an integer number`() {
            val expression = getExpression("10")

            assertThat(expression).isInstanceOf(IntegerValue::class.java)
            val i = expression as IntegerValue
            assertThat(i.intValue).isEqualTo(10)
        }

        @Test
        fun `parse a negative integer number`() {
            val expression = getExpression("-10")

            assertThat(expression).isInstanceOf(IntegerValue::class.java)
            val i = expression as IntegerValue
            assertThat(i.intValue).isEqualTo(-10)
        }

        @Test
        fun `parse a float number`() {
            val expression = getExpression("10.5")

            assertThat(expression).isInstanceOf(FloatValue::class.java)
            val i = expression as FloatValue
            assertThat(i.floatValue).isEqualTo(10.5)
        }

        @Test
        fun `parse a negative float number`() {
            val expression = getExpression("-1.3")

            assertThat(expression).isInstanceOf(FloatValue::class.java)
            val i = expression as FloatValue
            assertThat(i.floatValue).isEqualTo(-1.3)
        }

        @Test
        fun `parse a float number without leading zero`() {
            val expression = getExpression(".3")

            assertThat(expression).isInstanceOf(FloatValue::class.java)
            val i = expression as FloatValue
            assertThat(i.floatValue).isEqualTo(0.3)
        }

        @Test
        fun `parse a negative float number without leading zero`() {
            val expression = getExpression("-.3")

            assertThat(expression).isInstanceOf(FloatValue::class.java)
            val i = expression as FloatValue
            assertThat(i.floatValue).isEqualTo(-0.3)
        }
    }

    @Nested
    inner class StringTest {

        @Test
        fun `parse a string`() {
            val expression = getExpression("\"a\"")

            assertThat(expression).isInstanceOf(StringValue::class.java)
            val s = expression as StringValue
            assertThat(s.strValue).isEqualTo("a")

        }

        @Test
        fun `parse an empty string`() {
            val expression = getExpression("\"\"")

            assertThat(expression).isInstanceOf(StringValue::class.java)
            val s = expression as StringValue
            assertThat(s.strValue).isEqualTo("")

        }

        @Test
        fun `parse a string with a newline`() {
            val expression = getExpression("\"a\\n\"")

            assertThat(expression).isInstanceOf(StringValue::class.java)
            val s = expression as StringValue
            assertThat(s.strValue).isEqualTo("a\n")

        }
    }

    @Nested
    inner class IdentifierTest {
        @Test
        fun `parse a normal identifier`() {
            val expression = getExpression("abc!")

            assertThat(expression).isInstanceOf(Identifier::class.java)
            val id = expression as Identifier

            assertThat(id.name).isEqualTo("abc!")

            assertThat(id.prettyPrint()).isEqualTo("abc!")
        }

        @Test
        fun `parse an operator as identifier`() {
            val expression = getExpression("(++)")

            assertThat(expression).isInstanceOf(Identifier::class.java)
            val id = expression as Identifier

            assertThat(id.name).isEqualTo("++")

            assertThat(id.prettyPrint()).isEqualTo("(++)")
        }
    }

    @Nested
    inner class BooleanTest {
        @Test
        fun `parse true`() {
            val expression = getExpression("true")

            assertThat(expression).isInstanceOf(BooleanValue::class.java)
            val bool = expression as BooleanValue
            assertThat(bool.boolValue).isTrue
        }

        @Test
        fun `parse false`() {
            val expression = getExpression("false")

            assertThat(expression).isInstanceOf(BooleanValue::class.java)
            val bool = expression as BooleanValue
            assertThat(bool.boolValue).isFalse
        }
    }
}
