package de.gma.gamma.builtins.assertions

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateAssertion(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "assert", AssertFunction, "assert <list-of-pairs> – tests every assertion in the list of pairs. Print only not equal cases")
    // @Formatter:on
}
