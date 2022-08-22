package de.gma.gamma.evaluation.types

import de.gma.gamma.datatypes.values.BooleanValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TypeCheckTest : BaseEvaluationTest() {

    @ParameterizedTest
    @CsvSource(
        value = [
            "false,true",
            "{1;2;3},false",
            "10.4,false",
            "10,false",
            "\"a\",false",
            "[a : a],false",
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a boolean`(code: String, expected: Boolean) {
        val result = execute("is-boolean? $code") as BooleanValue

        assertThat(result.boolValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "false,false",
            "{1;2;3},true",
            "10.4,false",
            "10,false",
            "\"a\",false",
            "[a : a],false",
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a list`(code: String, expected: Boolean) {
        val result = execute("is-list? $code") as BooleanValue

        assertThat(result.boolValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "false,false",
            "{1;2;3},false",
            "10.4,true",
            "10,false",
            "\"a\",false",
            "[a : a],false",
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a float`(code: String, expected: Boolean) {
        val result = execute("is-float? $code") as BooleanValue

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
            "[a : a],false",
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is an integer`(code: String, expected: Boolean) {
        val result = execute("is-integer? $code") as BooleanValue

        assertThat(result.boolValue).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "false,false",
            "{1;2;3},false",
            "10.4,false",
            "10,false",
            "\"a\",true",
            "[a : a],false",
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a string`(code: String, expected: Boolean) {
        val result = execute("is-string? $code") as BooleanValue

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
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a function`(code: String, expected: Boolean) {
        val result = execute("is-function? $code") as BooleanValue

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
            "(r* {:a -> 10}),true",
            ":hallo,false",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a record`(code: String, expected: Boolean) {
        val result = execute("is-record? $code") as BooleanValue

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
            "(r* {:a -> 10}),false",
            ":hallo,true",
            "(),false",
            "this,false"
        ]
    )
    fun `check if object is a property`(code: String, expected: Boolean) {
        val result = execute("is-property? $code") as BooleanValue

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
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),true",
            "this,false"
        ]
    )
    fun `check if object is an emptyvalue`(code: String, expected: Boolean) {
        val result = execute("is-emptyvalue? $code") as BooleanValue

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
            "(r* {:a -> 10}),false",
            ":hallo,false",
            "(),false",
            "this,true"
        ]
    )
    fun `check if object is an module`(code: String, expected: Boolean) {
        val result = execute("is-module? $code") as BooleanValue

        assertThat(result.boolValue).isEqualTo(expected)
    }
}
