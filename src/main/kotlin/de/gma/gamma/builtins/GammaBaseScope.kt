package de.gma.gamma.builtins

import de.gma.gamma.builtins.assertions.populateAssertion
import de.gma.gamma.builtins.comparison.populateComparison
import de.gma.gamma.builtins.control.populateControl
import de.gma.gamma.builtins.io.populateIO
import de.gma.gamma.builtins.list.populateList
import de.gma.gamma.builtins.namespaces.populateNamespace
import de.gma.gamma.builtins.numerical.populateNumerical
import de.gma.gamma.builtins.shell.populateShell
import de.gma.gamma.builtins.shell.resetShell
import de.gma.gamma.builtins.types.populateTypes
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.parser.Parser
import java.io.BufferedReader
import java.io.InputStreamReader

const val CWD_NAME = "CWD"

object GammaBaseScope : ModuleScope("global", null) {
    init {
        populateTypes(this)
        populateControl(this)
        populateIO(this)
        populateNumerical(this)
        populateList(this)
        populateComparison(this)
        populateNamespace(this)
        populateAssertion(this)
        populateShell(this)

        read("/builtin/functional.gma")
    }

    fun reset() {
        resetShell(this)
    }

    private fun read(resourceName: String) {
        val inStream = this::class.java.getResourceAsStream(resourceName) ?: return
        val text = BufferedReader(InputStreamReader(inStream)).readText()

        val parser = Parser(text, resourceName)
        var expression = parser.nextExpression()
        while (expression != null) {
            expression.evaluate(this)
            expression = parser.nextExpression()
        }
    }
}
