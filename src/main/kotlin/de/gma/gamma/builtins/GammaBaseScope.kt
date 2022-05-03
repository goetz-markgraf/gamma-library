package de.gma.gamma.builtins

import de.gma.gamma.builtins.io.populateIO
import de.gma.gamma.builtins.operator.populateOperator
import de.gma.gamma.interpreter.MapScope

class GammaBaseScope() : MapScope(null) {
    init {
        populateIO(this)
        populateOperator(this)
    }
}
