package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.interpreter.Scope

fun populateOperator(scope: Scope) {
    bindWithDoc(scope, "+", AddFunction(), "<a> + <b> - adds two number values")
    bindWithDoc(scope, "*", TimesFunction(), "<a> * <b> - multiplies two number values")
}
