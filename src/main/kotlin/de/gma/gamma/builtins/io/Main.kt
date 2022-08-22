package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateIO(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "print", PrintFunction, "prints a value to standard out")
    bindWithDoc(scope, "print*", PrintStarFunction, "prints a value to standard out without a newline at the end")
    bindWithDoc(scope, "read-lines", ReadLinesFunction, "reads the content of <filename> as a list of strings")
    bindWithDoc(scope, "split", SplitFunction, "splits a string separated by blank into a list of strings")
    bindWithDoc(scope, "split-by", SplitByFunction, "splits a string separated by <separator> into a list of strings")
    bindWithDoc(scope, "join", JoinFunction, "joins all elements of <list> together as one string, seperated by a blank")
    bindWithDoc(scope, "join-by", JoinByFunction, "joins all elements of <list> together as one string, seperated by <string>")
    // @Formatter:on
}
