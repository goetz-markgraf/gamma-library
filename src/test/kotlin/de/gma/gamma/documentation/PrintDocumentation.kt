package de.gma.gamma.documentation

import de.gma.gamma.datatypes.scope.ModuleScope
import org.junit.jupiter.api.Test

class PrintDocumentation {

    @Test
    fun `print all documentation strings`() {
        val scope = ModuleScope("test").parent as ModuleScope

        scope.getAllNames().forEach {
            println(scope.getRemark(it))
        }
    }
}
