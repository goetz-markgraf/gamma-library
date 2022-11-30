package de.gma.gamma.evaluation

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.parser.Parser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class BaseEvaluationTest {
    private val baseScope = GammaBaseScope
    protected lateinit var scope: ModuleScope

    private val output = StringBuilder()

    @BeforeEach
    fun setUp() {
        scope = ModuleScope("test", baseScope)
        baseScope.doPrint = { output.append(it) }
    }

    @AfterEach
    fun cleanUp() {
        baseScope.reset()
    }

    protected fun execute(code: String): Value? {
        val parser = Parser(code)

        var expr = parser.nextExpression(-1)
        var ret: Value? = null

        while (expr != null) {
            ret = expr.evaluate(scope)
            expr = parser.nextExpression(-1)
        }

        return ret
    }

    protected fun assertOutput(expected: String) {
        assertThat(output.toString()).isEqualTo(expected)
    }
}
