package de.gma.gamma.evaluation.types

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TypeConversionTest : BaseEvaluationTest() {

    @Nested
    inner class CanBeUsedAs {
        @ParameterizedTest
        @CsvSource(
            value = [
                "false,true",
                "{1;2;3},true",
                "10.4,true",
                "10,true",
                "\"a\",true",
                "[a : a],true",
                "{:a -> 10},true",
                ":hallo,true",
                "(),true",
                "this,true"
            ]
        )
        fun `check if object can be used as a boolean`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-boolean? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,true",
                "{1;2;3},true",
                "{},true",
                "10.4,true",
                "10,true",
                "\"a\",true",
                "[a : a],true",
                "{:a -> 10},true",
                ":hallo,true",
                "(),true",
                "this,true"
            ]
        )
        fun `check if object can be used as a list`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-list? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,false",
                "{1;2;3},false",
                "10.4,true",
                "10,true",
                "\"a\",false",
                "\"10\",true",
                "\"10.5\",true",
                "[a : a],false",
                "{:a -> 10},false",
                ":hallo,false",
                "(),false",
                "this,false"
            ]
        )
        fun `check if object can be used as a float`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-float? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,false",
                "{1;2;3},false",
                "10.4,false",
                "10,true",
                "\"a\",false",
                "\"10\",true",
                "\"10.5\",false",
                "[a : a],false",
                "{:a -> 10},false",
                ":hallo,false",
                "(),false",
                "this,false"
            ]
        )
        fun `check if object can be used as an integer`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-integer? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,true",
                "{1;2;3},true",
                "10.4,true",
                "10,true",
                "\"a\",true",
                "[a : a],true",
                "{:a -> 10},true",
                ":hallo,true",
                "(),true",
                "this,true"
            ]
        )
        fun `check if object can be used as a string`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-string? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,false",
                "{1;2;3},false",
                "10.4,false",
                "10,false",
                "\"a\",false",
                "[a : a],true",
                "{:a -> 10},false",
                ":hallo,true",
                "(),false",
                "this,false"
            ]
        )
        fun `check if object can be used as a function`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-function? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,false",
                "{1;2;3},false",
                "{},true",
                "10.4,false",
                "10,false",
                "\"a\",false",
                "[a : a],false",
                "{:a -> 10},true",
                ":hallo,false",
                "(),false",
                "this,false"
            ]
        )
        fun `check if object can be used as a record`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-record? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,true",
                "{1;2;3},true",
                "10.4,true",
                "10,true",
                "\"a\",true",
                "[a : a],true",
                "{:a -> 10},true",
                ":hallo,true",
                "(),true",
                "this,true"
            ]
        )
        fun `check if object can be used as a property`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-property? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,false",
                "{1;2;3},false",
                "10.4,false",
                "10,false",
                "\"a\",false",
                "[a : a],false",
                "{:a -> 10},false",
                ":hallo,false",
                "(),true",
                "this,false"
            ]
        )
        fun `check if object can be used as void`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-void? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "false,false",
                "{1;2;3},false",
                "10.4,false",
                "10,false",
                "\"a\",false",
                "[a : a],false",
                "{:a -> 10},false",
                ":hallo,false",
                "(),false",
                "this,true"
            ]
        )
        fun `check if object can be used as an module`(code: String, expected: Boolean) {
            val result = execute("can-be-used-as-module? $code") as BooleanValue

            assertThat(result.boolValue).isEqualTo(expected)
        }
    }

    @Nested
    inner class ConvertList {

        @Test
        fun `convert a record to a list`() {
            val result = execute("{:a -> 1} ▷ to-list") as ListValue

            assertThat(result.allItems()).hasSize(1)
                .first().isInstanceOf(ListValue::class.java)
        }

        @Test
        fun `covert an empty record a an empty list`() {
            val result = execute(" { } ▷ to-record ▷ to-list") as ListValue

            assertThat(result.allItems()).isEmpty()
        }

        @Test
        fun `a string is converted to a list of all characters`() {
            val result = execute("\"Test\" ▷ to-list") as ListValue

            assertThat(result.allItems()).hasSize(1)
            assertThat(result.allItems()).first().hasFieldOrPropertyWithValue("strValue", "Test")
        }

        @Test
        fun `an empty string is converted to an empty list`() {
            val result = execute("\"\" ▷ to-list") as ListValue

            assertThat(result.allItems()).isEmpty()
        }
    }

    @Nested
    inner class ConvertRecord {

        @Test
        fun `convert a list to a record`() {
            val result = execute("{1} ▷ map [it : {:a , it}] ▷ to-record") as RecordValue

            assertThat(result.getPropertyNames()).containsExactly("a")
        }

        @Test
        fun `covert an empty list a an empty record`() {
            val result = execute(" { } ▷ to-record") as RecordValue

            assertThat(result.getPropertyNames()).isEmpty()
        }

        @Test
        fun `an empty record is considered empty`() {
            val result = execute("{} ▷ to-record ▷ is-empty?") as BooleanValue

            assertThat(result.boolValue).isTrue
        }
    }
}
