package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateOperator(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "+", AddFunction, "<a> + <b> - adds two number values")
    bindWithDoc(scope, "-", SubtractFunction, "<a> - <b> - subtract <b> from <a>")
    bindWithDoc(scope, "*", TimesFunction, "<a> * <b> - multiplies two number values")
    bindWithDoc(scope, "×", TimesFunction, "<a> × <b> - multiplies two number values")
    bindWithDoc(scope, "/", DividedByFunction, "<a> / <b> - divides <a> by <b>")
    bindWithDoc(scope, "÷", DividedByFunction, "<a> ÷ <b> - divides <a> by <b>")
    bindWithDoc(scope, "^", PowerToFunction, "<a> ^ <b> - raise <a> to the <b>th power")
    bindWithDoc(scope, "neg", NegativeFunction, "neg <a> - return the negative of a number")
    bindWithDoc(scope, "|>", PipeFunction, "<value> |> <function> - calls <function> with <value> as parameter")
    bindWithDoc(scope, "▷", PipeFunction, "<value> ► <function> - calls <function> with <value> as parameter")
    bindWithDoc(scope, "=>>", MapPipeFunction, "<list> =>> <function> - calls map on <list> with <function> as parameter")
    // @Formatter:on
}
