package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.CWD_NAME
import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.scope.Scope

fun populateShell(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "sh", ShellFunction, "executes the given command in the current working directory and returns the output as a list of strings (lines)")
    bindWithDoc(scope, "$", ShellFunction, "executes the given command in the current working directory and returns the output as a list of strings (lines)")
    bindWithDoc(scope, "cd", CDFunction, "changes the current working directory to a new directory")
    // @Formatter:on

    scope.bindValue(
        CWD_NAME,
        StringValue.build(System.getProperty("user.dir")),
        Remark.buildDoc("returns the current working directory")
    )
}

fun resetShell(scope: Scope) {
    scope.bindValue(
        CWD_NAME,
        StringValue.build(System.getProperty("user.dir")),
        Remark.buildDoc("returns the current working directory"),
        false
    )
}
