package de.gma.gamma.documentation

import de.gma.gamma.builtins.GammaBaseScope
import org.junit.jupiter.api.Test

class PrintDocumentation {

    @Test
    fun `print all documentation strings`() {
        val scope = GammaBaseScope

        scope.getAllNames().forEach {
            println(scope.getRemark(it))
        }
    }
}
