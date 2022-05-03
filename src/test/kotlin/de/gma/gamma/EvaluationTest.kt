package de.gma.gamma

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.GValueType
import de.gma.gamma.interpreter.MapScope
import de.gma.gamma.parser.Parser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EvaluationTest {
    private val baseScope = GammaBaseScope()

    @Test
    fun `first try`() {
        val code = """
            let a = 1 + 2 * 3
            let b = -1
            let c = true
            print (c ? a : b)
        """.trimIndent()

        val ret = execute(code)

        assertThat(ret!!.type).isEqualTo(GValueType.INTEGER)
        assertThat(ret.prettyPrint()).isEqualTo("7")
    }

    private fun execute(code: String): GValue? {
        val parser = Parser(code, "Script")
        val scope = getScope()

        var expr = parser.nextExpression(-1)
        var ret: GValue? = null

        while (expr != null) {
            ret = expr.evaluate(scope)
            expr = parser.nextExpression(-1)
        }

        return ret
    }

    private fun getScope() = MapScope(baseScope)
}
