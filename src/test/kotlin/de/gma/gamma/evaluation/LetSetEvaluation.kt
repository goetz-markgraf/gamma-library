package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.scoped.ScopedFunction
import de.gma.gamma.datatypes.values.IntegerValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LetSetEvaluation : BaseEvaluationTest() {

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
    fun `prepare an expression`() {
        val code = """
            let expr = 10 + 20
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(IntegerValue::class.java)
        assertThat(scope.getValue("expr")).isInstanceOf(IntegerValue::class.java)
        assertThat((result as IntegerValue).intValue).isEqualTo(30)
    }

    @Test
    fun `prepare an identifier`() {
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
        assertThat(i.intValue).isEqualTo(30)
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
        assertThat(i.intValue).isEqualTo(30)
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
}
