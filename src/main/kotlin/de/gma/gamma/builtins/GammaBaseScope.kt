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
import de.gma.gamma.builtins.types.populateTypes
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Parser

class GammaBaseScope(
    printFunction: ((String) -> Unit)? = null
) : ModuleScope("global", null) {

    var doPrint: (String) -> Unit = printFunction ?: { text: String -> print(text) }

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

    private fun applyCode(code: String, sourceName: String) {
        val parser = Parser(code, sourceName)
        var expression = parser.nextExpression()
        while (expression != null) {
            expression.evaluate(this)
            expression = parser.nextExpression()
        }
    }

    companion object {
        /**
         * Walks up the scope chain to find the nearest [GammaBaseScope] instance.
         * Every scope chain must be rooted at a [GammaBaseScope]; throws if not found.
         */
        fun from(scope: Scope): GammaBaseScope {
            var s: Scope? = scope
            while (s != null) {
                if (s is GammaBaseScope) return s
                s = s.parent
            }
            throw IllegalStateException("No GammaBaseScope found in scope chain")
        }
    }
}
