package de.gma.gamma

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scoped.ScopedExpression
import de.gma.gamma.datatypes.scoped.ScopedFunction
import de.gma.gamma.datatypes.scoped.ScopedIdentifier
import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.interpreter.MapScope
import de.gma.gamma.parser.Parser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EvaluationTest {
    private val baseScope = GammaBaseScope()
    private var scope = MapScope(baseScope)

    @BeforeEach
    fun setUp() {
        scope = MapScope(baseScope)
    }

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
    fun `prepare a function`() {
        val code = """
            let add = [a b ->
                a + b ]
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ScopedFunction::class.java)
        assertThat(scope.getValue("add")).isInstanceOf(ScopedFunction::class.java)
    }

    @Test
    fun `prepare an expression`() {
        val code = """
            let expr = 10 + 20
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ScopedExpression::class.java)
        assertThat(scope.getValue("expr")).isInstanceOf(ScopedExpression::class.java)
        assertThat(result?.evaluate(scope)).isInstanceOf(IntegerValue::class.java)
    }

    @Test
    fun `prepare an identifier`() {
        val code = """
            let id = a
        """.trimIndent()

        val result = execute(code)

        assertThat(result).isInstanceOf(ScopedIdentifier::class.java)
        assertThat(scope.getValue("id")).isInstanceOf(ScopedIdentifier::class.java)
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

    private fun execute(code: String): Value? {
        val parser = Parser(code, "Script")

        var expr = parser.nextExpression(-1)
        var ret: Value? = null

        while (expr != null) {
            ret = expr.evaluate(scope)
            expr = parser.nextExpression(-1)
        }

        return ret
    }

}
