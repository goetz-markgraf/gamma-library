package de.gma.gamma.evaluation

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.parser.Parser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.Charset

open class BaseEvaluationTest {
    private val output = ByteArrayOutputStream()
    private val baseScope = GammaBaseScope
    protected lateinit var scope: ModuleScope

    @BeforeEach
    fun setUp() {
        baseScope.output = PrintStream(output)
        scope = ModuleScope("test", baseScope)
    }

    @AfterEach
    fun cleanUp() {
        print(output.toString(Charset.defaultCharset()))
        baseScope.output.close()
        output.close()
        output.reset()
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
        assertThat(output.toString(Charset.defaultCharset())).isEqualTo(expected)
        output.close()
        output.reset()
    }
}
