package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateIO(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "print", PrintFunction, "print <value> - prints a value to standard out")
    bindWithDoc(scope, "print*", PrintStarFunction, "print* <value> - prints a value to standard out without a newline at the end")
    bindWithDoc(scope, "read-lines", ReadLinesFunction, "read-lines <filename> - reads the content of <filename> as a list of strings")
    bindWithDoc(scope, "split", SplitFunction, "split <string> - splits a string separated by blank into a list of strings")
    bindWithDoc(scope, "split-by", SplitByFunction, "split-by <separator> <string> - splits a string separated by <separator> into a list of strings")
    bindWithDoc(scope, "join", JoinFunction, "join <string> <list> - joins all elements of <list> together as one string, seperated by <string>")
    // @Formatter:on
}
