package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateShell(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "sh", ExecuteFunction, "executes the given command in the current working directory and returns the output")
    // @Formatter:on
}
