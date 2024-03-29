package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.PairValue
import de.gma.gamma.datatypes.values.VoidValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EvaluationTest : BaseEvaluationTest() {

    @Test
    fun `simple test with assignments and ternary operator`() {
        val code = """
            let a = 1 + 2 * 3
            let b = -1
            let c = true
            print* (c ? a : b)
        """.trimIndent()

        val ret = execute(code) as IntegerValue

        assertThat(ret.longValue).isEqualTo(7L)
        assertOutput("7")
    }

    @Test
    fun `functions returns a working closure`() {
        val code = """
            let createAccount start =
                let balance! = start
                [ deposit :
                    set balance! = (balance! + deposit)
                    balance! ]
                
            let account = createAccount 10
            print (account 10)
            print (account 20)
            print (account 100)
        """.trimIndent()

        val result = execute(code)

        assertThat(result!!.prettyPrint()).isEqualTo("140")
        assertOutput(
            """
            20
            40
            140
            
        """.trimIndent()
        )
    }

    @Test
    fun `call with Empty as parameter`() {
        val code = "print* ()"

        execute(code) as VoidValue

        assertOutput("()")
    }

    @Test
    fun `define and call a function with no parameter`() {
        val code = """
            let greet = [ () :
                print "Hello"
            ]
            
            greet ()
        """.trimIndent()

        val result = execute(code) as StringValue

        assertThat(result.strValue).isEqualTo("Hello")
        assertOutput("Hello\n")
    }

    @Test
    fun `define and call a function with no parameter using shorthand notation`() {
        val code = """
            let greet () =
                print "Hello, World"
                
            greet()
        """.trimIndent()

        val result = execute(code) as StringValue
        assertThat(result.strValue).isEqualTo("Hello, World")
        assertOutput("Hello, World\n")
    }

    @Test
    fun `create a complex list of lists`() {
        val code = """
            let a = 10
            {
                a > 2 -> "groesser"
                a < 2 -> "kleiner"
                else -> "gleich"
            }
        """.trimIndent()

        val result = execute(code) as ListValue

        assertThat(result.size()).isEqualTo(3)
        assertThat(result.first()).isInstanceOf(PairValue::class.java)
        assertThat(result.last()).isInstanceOf(PairValue::class.java)
    }

    @Test
    fun `recursive sum`() {
        val code = """
            let sum lst =
                size lst = 0
                    ? 0
                    : first lst + sum (tail lst)
                
            sum {1, 2, 3}
        """.trimIndent()

        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(6L)
    }
}
