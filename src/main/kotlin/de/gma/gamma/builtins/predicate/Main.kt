package de.gma.gamma.builtins.predicate

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

fun populateComparison(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "when", WhenFunction, "when <list of predicate/expression lists> - " +
            "checks each predicate to evaluate to first expression with a true predicate")
    bindWithDoc(scope, "=", EqualFunction, "<a> = <b> - checks if <a> is equal to <b>")
    bindWithDoc(scope, "!=", NotEqualFunction, "<a> != <b> - checks if <a> is not equal to <b>")
    bindWithDoc(scope, "≠", NotEqualFunction, "<a> ≠ <b> - checks if <a> is not equal to <b>")
    bindWithDoc(scope, ">", GreaterThanFunction, "<a> > <b> - checks if the first value is greater than the second")
    bindWithDoc(scope, ">=", GreaterThanOrEqualFunction, "<a> >= <b> - checks if the first value is greater than or equal to the second")
    bindWithDoc(scope, "≥", GreaterThanOrEqualFunction, "<a> ≥ <b> - checks if the first value is greater than or equal to the second")
    bindWithDoc(scope, "<", LessThanFunction, "<a> < <b> - checks if the first value is less than the second")
    bindWithDoc(scope, "<=", LessThanOrEqualFunction, "<a> <= <b> - checks if the first value is less than or equal to the second")
    bindWithDoc(scope, "≤", LessThanOrEqualFunction, "<a> ≤ <b> - checks if the first value is less than or equal to the second")
    bindWithDoc(scope, "&", AndFunction, "<a> & <b> - returns true if <a> and <b> are both true")
    bindWithDoc(scope, "∧", AndFunction, "<a> ⋀ <b> - returns true if <a> and <b> are both true")
    bindWithDoc(scope, "|", OrFunction, "<a> | <b> - returns true if either <a> or <b> or both are true")
    bindWithDoc(scope, "∨", OrFunction, "<a> ⋁ <b> - returns true if either <a> or <b> or both are true")
    bindWithDoc(scope, "not", NotFunction, "not <a> - returns true if <a> is false and false if <a> is true")
    // @Formatter:on

    scope.bindValue(
        "else",
        BooleanValue.build(true),
        Remark.buildDoc("else - equal to true, can be used in when expressions")
    )
}
