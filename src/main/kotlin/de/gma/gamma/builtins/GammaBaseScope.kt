package de.gma.gamma.builtins

import de.gma.gamma.builtins.assertions.populateAssertion
import de.gma.gamma.builtins.io.populateIO
import de.gma.gamma.builtins.list.populateList
import de.gma.gamma.builtins.namespaces.populateNamespace
import de.gma.gamma.builtins.numerical.populateOperator
import de.gma.gamma.builtins.predicate.populateComparison
import de.gma.gamma.datatypes.scope.ModuleScope

object GammaBaseScope : ModuleScope("global", null) {
    init {
        populateIO(this)
        populateOperator(this)
        populateList(this)
        populateComparison(this)
        populateNamespace(this)
        populateAssertion(this)
    }
}
