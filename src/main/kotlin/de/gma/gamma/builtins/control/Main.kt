package de.gma.gamma.builtins.control

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.DebugValue
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.scope.Scope

private const val CALLS_FUNCTION_WITH_VALUE_AS_PARAMETER = "calls [function] with [value] as parameter"

fun populateControl(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "|>", PipeFunction, CALLS_FUNCTION_WITH_VALUE_AS_PARAMETER)
    bindWithDoc(scope, "▷", PipeFunction, CALLS_FUNCTION_WITH_VALUE_AS_PARAMETER)
    bindWithDoc(scope, "\u22b3", PipeFunction, CALLS_FUNCTION_WITH_VALUE_AS_PARAMETER)
    // @Formatter:on

    scope.bindValue(
        "debug",
        DebugValue.build(),
        Remark.buildDoc("evaluates the DebugValue for debug purposes")
    )

    scope.bindValue(
        "break",
        DebugValue.build(),
        Remark.buildDoc("evaluates the DebugValue for debug purposes")
    )
}
