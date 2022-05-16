package de.gma.gamma.builtins.operator

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateOperator(scope: Scope) {
    bindWithDoc(scope, "+", AddFunction(), "<a> + <b> - adds two number values")
    bindWithDoc(scope, "*", TimesFunction(), "<a> * <b> - multiplies two number values")
    bindWithDoc(scope, ">", GreaterThanFunction(), "<a> > <b> - checks if the first value is greater than the second")
    bindWithDoc(
        scope,
        ">=",
        GreaterThanOrEqualFunction(),
        "<a> >= <b> - checks if the first value is greater than or equal to the second"
    )
    bindWithDoc(scope, "<", LessThanFunction(), "<a> > <b> - checks if the first value is less than the second")
    bindWithDoc(
        scope,
        ">=",
        LessThanOrEqualFunction(),
        "<a> > <b> - checks if the first value is less than or equal to the second"
    )
}
