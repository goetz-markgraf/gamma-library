package de.gma.gamma.builtins

import de.gma.gamma.builtins._gamma.codeFunctional
import de.gma.gamma.builtins._gamma.codeIo
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

object GammaBaseScope : ModuleScope("global", null) {
    val output = StringBuilder()

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

        applyCode(codeIo, "code.gma")
        applyCode(codeFunctional, "functional.gma")
    }

    fun reset() {
        resetShell(this)
        output.clear()
    }

    fun doPrint(text: String) {
        output.append(text)
        print(text)
    }

    private fun applyCode(code: String, sourceName: String) {
        val parser = Parser(code, sourceName)
        var expression = parser.nextExpression()
        while (expression != null) {
            expression.evaluate(this)
            expression = parser.nextExpression()
        }
    }
}
