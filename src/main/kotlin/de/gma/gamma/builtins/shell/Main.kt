package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope

const val CWD_NAME = "CWD"
const val ENV_NAME = "env"
const val SYSTEM_NAME = "system"

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

    scope.bindValue(
        ENV_NAME,
        createEnvValue(),
        Remark.buildDoc("holds all environment variables and their values as a record")
    )

    scope.bindValue(
        SYSTEM_NAME,
        createSystemValue(),
        Remark.buildDoc("holds all java system properties their values as a record (names with a '.' are replaced by '-')")
    )
}

fun resetShell(scope: Scope) {
    scope.bindValue(
        CWD_NAME,
        StringValue.build(System.getProperty("user.dir")),
        Remark.buildDoc("resets the current working directory"),
        false
    )
}

private fun createEnvValue() =
    RecordValue(builtInSource, nullPos, nullPos,
        System.getenv().mapValues { StringValue.build(it.value) }
    )

private fun createSystemValue() =
    RecordValue(builtInSource, nullPos, nullPos,
        System.getProperties()
            .stringPropertyNames()
            .associate { Pair(it.replace('.', '-'), System.getProperty(it) ?: "") }
            .mapValues { StringValue.build(it.value) }
    )
