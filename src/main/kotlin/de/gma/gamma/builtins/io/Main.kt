package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateIO(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "print", PrintFunction(), "print <value> - prints a value to standard out")
    bindWithDoc(scope, "read-lines", ReadLinesFunction(), "read-lines <filename> - reads the content of <filename> as a list of strings")
    bindWithDoc(scope, "split", SplitFunction(), "split <string> - splits a string separated by blank into a list of strings")
    // @Formatter:on
}
