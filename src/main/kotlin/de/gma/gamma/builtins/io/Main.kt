package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateIO(scope: Scope) {
    bindWithDoc(scope, "print", PrintFunction(), "print <value> - prints a value to standard out")
}
