package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.scope.ScopeException
import de.gma.gamma.datatypes.scoped.ScopedFunction
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.parser.EvaluationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class LetSetEvaluation : BaseEvaluationTest() {

    @Test
    fun `bind an expression`() {
        val code = """
            let expr = 10 + 20
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(IntegerValue::class.java)
        assertThat(scope.getValue("expr")).isInstanceOf(IntegerValue::class.java)
        assertThat((result as IntegerValue).longValue).isEqualTo(30)
    }

    @Test
    fun `bind an identifier`() {
        val code = """
            let a = 10
            let id = a
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(IntegerValue::class.java)
        assertThat(scope.getValue("id")).isInstanceOf(IntegerValue::class.java)
    }

    @Test
    fun `create and call a function`() {
        val code = """
            let add = [ a b -> a + b ]
            
            add 10 20
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(IntegerValue::class.java)
        val i = result as IntegerValue
        assertThat(i.longValue).isEqualTo(30)
    }

    @Test
    fun `create and call a curried function`() {
        val code = """
            let add = [ a b -> a + b ]
            
            let addTen = add 10
            
            addTen 20
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(IntegerValue::class.java)
        val i = result as IntegerValue
        assertThat(i.longValue).isEqualTo(30)
    }


    @Test
    fun `bind a function`() {
        val code = """
            let add = [a b ->
                a + b ]
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ScopedFunction::class.java)
        assertThat(scope.getValue("add")).isInstanceOf(ScopedFunction::class.java)
    }

    @Test
    fun `bind a function v2`() {
        val code = """
            let add a b =
                a + b 
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ScopedFunction::class.java)
        assertThat(scope.getValue("add")).isInstanceOf(ScopedFunction::class.java)
    }

    @Test
    fun `bind a function with no params`() {
        val code = """
            let doIt () =
                print "Hello, World." 
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ScopedFunction::class.java)
        assertThat(scope.getValue("doIt")).isInstanceOf(ScopedFunction::class.java)
    }


    @Test
    fun `mutate an identifier`() {
        val code = """
            let a! = 10
            print a!
            
            set a! = 20
            print a!
        """.trimIndent()

        val result = execute(code)
        assertThat(result!!.prettyPrint()).isEqualTo("20")
        assertThat(scope.getValue("a!").prettyPrint()).isEqualTo("20")
    }

    @Test
    fun `error if an identifier is not bound`() {
        assertThatThrownBy {
            execute("a")
        }.isInstanceOf(ScopeException::class.java)
            .hasMessage("Id a is undefined.")
    }

    @Test
    fun `error if an identifier is bound twice`() {
        assertThatThrownBy {
            execute("let a = 10; let a = 20")
        }.isInstanceOf(ScopeException::class.java)
            .hasMessage("Id a is already defined.")
    }

    @Test
    fun `error if an non-existing identifier is mutated`() {
        assertThatThrownBy {
            execute("set a! = 20")
        }.isInstanceOf(EvaluationException::class.java)
            .hasMessage("Cannot find id a!")
    }
}
