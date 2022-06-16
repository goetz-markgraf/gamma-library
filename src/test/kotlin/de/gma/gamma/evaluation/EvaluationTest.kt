package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.list.PairValue
import de.gma.gamma.datatypes.list.SimpleList
import de.gma.gamma.datatypes.list.StringValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.UnitValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EvaluationTest : BaseEvaluationTest() {

    @Test
    fun `simple test with assignments and elvis operator`() {
        val code = """
            let a = 1 + 2 * 3
            let b = -1
            let c = true
            print (c ? a : b)
        """.trimIndent()

        val ret = execute(code)

        assertThat(ret!!).isInstanceOf(IntegerValue::class.java)
        assertThat(ret.prettyPrint()).isEqualTo("7")
    }

    @Test
    fun `functions returns a working closure`() {
        val code = """
            let createAccount start =
                let balance! = start
                [ deposit ->
                    set balance! = (balance! + deposit)
                    balance! ]
                
            let account = createAccount 10
            print (account 10)
            print (account 20)
            print (account 100)
        """.trimIndent()

        val result = execute(code)

        assertThat(result!!.prettyPrint()).isEqualTo("140")
    }

    @Test
    fun `call with Unit as parameter`() {
        val code = "print ()"

        val result = execute(code)

        assertThat(result).isInstanceOf(UnitValue::class.java)
    }

    @Test
    fun `define and call a function with no parameter`() {
        val code = """
            let greet = [ () ->
                print "Hello"
            ]
            
            greet ()
        """.trimIndent()

        val result = execute(code)

        assertThat(result!!.prettyPrint()).isEqualTo("\"Hello\"")
    }

    @Test
    fun `define and call a function with no parameter using shorthand notation`() {
        val code = """
            let greet () =
                print "Hello, World"
                
            greet()
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(StringValue::class.java)
    }

    @Test
    fun `create a complex list of lists`() {
        val code = """
            {
                a > 2 -> "groesser"
                a < 2 -> "kleiner"
                else -> "gleich"
            }
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ListValue::class.java)

        val l = result as SimpleList
        assertThat(l.size()).isEqualTo(3)
        assertThat(l.first()).isInstanceOf(PairValue::class.java)
        assertThat(l.last()).isInstanceOf(PairValue::class.java)
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
