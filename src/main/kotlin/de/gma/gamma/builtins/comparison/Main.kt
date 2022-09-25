package de.gma.gamma.builtins.comparison

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.builtins.control.PipelineFunction
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.BooleanValue

fun populateComparison(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "when", WhenFunction, "checks each predicate to evaluate to first expression with a true predicate")
    bindWithDoc(scope, "when*", WhenStarFunction, "checks each [predicate] called with [value] to evaluate to first expression with a true predicate")
    bindWithDoc(scope, "=", EqualFunction, "checks if [a] is equal to [b]")
    bindWithDoc(scope, "!=", NotEqualFunction, "checks if [a] is not equal to [a]")
    bindWithDoc(scope, "≠", NotEqualFunction, "checks if [a] is not equal to [a]")
    bindWithDoc(scope, ">", GreaterThanFunction, "checks if the first value is greater than the second")
    bindWithDoc(scope, ">=", GreaterThanOrEqualFunction, "checks if the first value is greater than or equal to the second")
    bindWithDoc(scope, "≥", GreaterThanOrEqualFunction, "checks if the first value is greater than or equal to the second")
    bindWithDoc(scope, "<", LessThanFunction, "checks if the first value is less than the second")
    bindWithDoc(scope, "<=", LessThanOrEqualFunction, "checks if the first value is less than or equal to the second")
    bindWithDoc(scope, "≤", LessThanOrEqualFunction, "checks if the first value is less than or equal to the second")
    bindWithDoc(scope, "&", AndFunction, "returns true if [a] and [a] are both true")
    bindWithDoc(scope, "∧", AndFunction, "returns true if [a] and [a] are both true")
    bindWithDoc(scope, "|", OrFunction, "returns true if either [a] or [a] or both are true")
    bindWithDoc(scope, "∨", OrFunction, "returns true if either [a] or [a] or both are true")
    bindWithDoc(scope, "not", NotFunction, "returns true if [a] is false and false if [a] is true")
    bindWithDoc(scope, "pipeline", PipelineFunction, "executes each [expression], always using [id] for the result of the previous one")
    // @Formatter:on

    scope.bindValue(
        "else",
        BooleanValue.build(true),
        Remark.buildDoc("else - equal to true, can be used in when expressions")
    )
}
