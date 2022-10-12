package de.gma.gamma.evaluation

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.parser.Parser
import org.junit.jupiter.api.BeforeEach

open class BaseEvaluationTest {
    private val baseScope = GammaBaseScope
    protected lateinit var scope: ModuleScope

    @BeforeEach
    fun setUp() {
        baseScope.reset()
        scope = ModuleScope("test", baseScope)
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

}
