package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.expressions.FunctionCall
import de.gma.gamma.datatypes.list.AbstractListValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.datatypes.values.StringValue
import de.gma.gamma.datatypes.values.UnitValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EvaluationTest : BaseEvaluationTest() {

    @Test
    fun `first try`() {
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
    fun `banking object`() {
        val code = """
            let createAccount =
                [ start ->
                  let balance! = start
                  [ withdraw ->
                    set balance! = (balance! + withdraw)
                    balance! ]
                ]
                
            let account = createAccount 10
            print (account 10)
            print (account 20)
            print (account 100)
        """.trimIndent()

        val result = execute(code)

        assertThat(result!!.prettyPrint()).isEqualTo("140")
    }

    @Test
    fun `empty call`() {
        val code = """
            let doIt = [ () ->
                print "Hello"
            ]
            
            doIt ()
        """.trimIndent()

        val result = execute(code)

        assertThat(result!!.prettyPrint()).isEqualTo("\"Hello\"")
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
            let greet () =
                print "Hello, World"
                
            greet()
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(StringValue::class.java)
    }

    @Test
    fun `create a complex list of lists` () {
        val code = """
            {
                a > 2 -> "groesser"
                a < 2 -> "kleiner"
                else -> "gleich"
            }
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(AbstractListValue::class.java)

        val l = result as ListValue
        assertThat(l.size()).isEqualTo(3)
        assertThat(l.first()).isInstanceOf(FunctionCall::class.java)
        assertThat(l.last()).isInstanceOf(FunctionCall::class.java)
    }
}
