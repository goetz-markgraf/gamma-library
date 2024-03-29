package de.gma.gamma.parser

import de.gma.gamma.datatypes.expressions.LetExpression
import de.gma.gamma.datatypes.expressions.OperaterCall
import de.gma.gamma.datatypes.expressions.SetExpression
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.functions.LambdaFunction
import de.gma.gamma.datatypes.list.ListLiteral
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

    @Test
    fun `let with more that one expression`() {
        // This is a wrongly formatted expression, the 20 will be considered a parameter for the function call "20 20"
        val source = """
            let a =
                10 + 20
                20
        """.trimIndent()

        val result = getExpressions(source)
        assertThat(result).hasSize(1)
        assertThat((result.first() as LetExpression).boundValue).isInstanceOf(OperaterCall::class.java)
    }

    @Test
    fun `single let expressions needn't be inserted`() {
        val source = """
            let a = {
                1
                2
                3
            }
        """.trimIndent()

        val result = getExpressions(source)
        assertThat(result).hasSize(1)
        assertThat((result.first() as LetExpression).boundValue).isInstanceOf(ListLiteral::class.java)
    }

    @Test
    fun `let with function`() {
        val source = """
            let add a b =
                print a
                print b
                a + b
        """.trimIndent()

        val result = getExpressions(source)

        assertThat(result).hasSize(1)
        assertThat(result.first()).isInstanceOf(LetExpression::class.java)
        val letExpression = result.first() as LetExpression
        assertThat(letExpression.identifier.name).isEqualTo("add")
        assertThat(letExpression.boundValue).isInstanceOf(LambdaFunction::class.java)

        val funVal = letExpression.boundValue as LambdaFunction
        assertThat(funVal.expressions).hasSize(3)
        assertThat(funVal.paramNames).hasSize(2)
    }

    @Test
    fun `let with function with no param`() {
        val source = """
            let doIt ()=
                print "Hello World"
        """.trimIndent()

        val result = getExpressions(source)

        assertThat(result).hasSize(1)
        assertThat(result.first()).isInstanceOf(LetExpression::class.java)
        val letExpression = result.first() as LetExpression
        assertThat(letExpression.identifier.name).isEqualTo("doIt")
        assertThat(letExpression.boundValue).isInstanceOf(LambdaFunction::class.java)

        val funVal = letExpression.boundValue as LambdaFunction
        assertThat(funVal.expressions).hasSize(1)
        assertThat(funVal.paramNames).hasSize(0)
    }

    @Test
    fun `illegal let expression`() {
        assertThatThrownBy {
            getExpression("let a 10")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Illegal Token '10 (NUMBER)' but was expecting =")
    }

    @Test
    fun `set expression with function`() {
        val result = getExpression("set a! = [ () : print 10 ]") as SetExpression

        assertThat(result.identifier.name).isEqualTo("a!")
        assertThat(result.boundValue).isInstanceOf(FunctionValue::class.java)
    }
}
