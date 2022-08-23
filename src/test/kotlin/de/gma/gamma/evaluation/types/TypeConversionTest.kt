package de.gma.gamma.evaluation.types

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TypeConversionTest : BaseEvaluationTest() {

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
            "false,false",
            "{1;2;3},false",
            "10.4,false",
            "10,false",
            "\"a\",false",
            "[a : a],false",
            "{:a -> 10},false",
            ":hallo,true",
            "(),false",
            "this,false"
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
    fun `check if object can be used as an emptyvalue`(code: String, expected: Boolean) {
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
