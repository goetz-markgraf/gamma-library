package de.gma.gamma.evaluation

import de.gma.gamma.datatypes.values.IntegerValue
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

}
