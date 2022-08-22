package de.gma.gamma.builtins

import de.gma.gamma.builtins.assertions.populateAssertion
import de.gma.gamma.builtins.comparison.populateComparison
import de.gma.gamma.builtins.control.populateControl
import de.gma.gamma.builtins.io.populateIO
import de.gma.gamma.builtins.list.populateList
import de.gma.gamma.builtins.namespaces.populateNamespace
import de.gma.gamma.builtins.numerical.populateNumerical
import de.gma.gamma.builtins.types.populateTypes
import de.gma.gamma.datatypes.scope.ModuleScope

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
    }
}
