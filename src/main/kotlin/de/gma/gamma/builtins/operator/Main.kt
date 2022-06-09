package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateOperator(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "+", AddFunction(), "<a> + <b> - adds two number values")
    bindWithDoc(scope, "*", TimesFunction(), "<a> * <b> - multiplies two number values")
    bindWithDoc(scope, "->", ToFunction(), "<a> -> <b> - creates a list from the two values")
    bindWithDoc(scope, "|>", PipeFunction(), "<value> |> <function> - calls <function> with <value> as parameter")
    // @Formatter:on
}
