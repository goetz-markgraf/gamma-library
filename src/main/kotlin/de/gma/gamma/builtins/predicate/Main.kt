package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

// @Formatter:off
fun populateComparison(scope: Scope) {
    bindWithDoc(scope, "when", WhenFunction(), "when <list of predicate/expression lists> - " +
            "checks each predicate to evaluate to first expression with a true predicate")
    bindWithDoc(scope, "=", EqualFunction(), "<a> = <b> - checks if <a> is equal to <b>")
    bindWithDoc(scope, ">", GreaterThanFunction(), "<a> > <b> - checks if the first value is greater than the second")
    bindWithDoc(scope, ">=", GreaterThanOrEqualFunction(), "<a> >= <b> - checks if the first value is greater than or equal to the second")
    bindWithDoc(scope, "<", LessThanFunction(), "<a> > <b> - checks if the first value is less than the second")
    bindWithDoc(scope, "<=", LessThanOrEqualFunction(), "<a> > <b> - checks if the first value is less than or equal to the second")
}
// @Formatter:on
