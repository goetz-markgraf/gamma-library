package de.gma.gamma.builtins.control

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateControl(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "|>", PipeFunction, "<value> |> <function> - calls <function> with <value> as parameter")
    bindWithDoc(scope, "▷", PipeFunction, "<value> ▷ <function> - calls <function> with <value> as parameter")
    // @Formatter:on
}
