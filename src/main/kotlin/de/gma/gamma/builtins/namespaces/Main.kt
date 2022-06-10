package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateNamespace(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "at", GetAtFunction(), "at <pos> <list> – returns the <pos>th element of this list")
    // @Formatter:on
}
