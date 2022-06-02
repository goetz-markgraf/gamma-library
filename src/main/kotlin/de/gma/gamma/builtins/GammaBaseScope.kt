package de.gma.gamma.builtins

import de.gma.gamma.builtins.io.populateIO
import de.gma.gamma.builtins.list.populateList
import de.gma.gamma.builtins.operator.populateOperator
import de.gma.gamma.datatypes.scope.ModuleScope

object GammaBaseScope : ModuleScope(null) {
    init {
        populateIO(this)
        populateOperator(this)
        populateList(this)
    }
}
