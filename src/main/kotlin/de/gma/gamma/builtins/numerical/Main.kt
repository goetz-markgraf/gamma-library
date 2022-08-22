package de.gma.gamma.builtins.numerical

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateNumerical(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "+", AddFunction, "adds two number values")
    bindWithDoc(scope, "-", SubtractFunction, "subtract <b> from <a>")
    bindWithDoc(scope, "*", TimesFunction, "multiplies two number values")
    bindWithDoc(scope, "×", TimesFunction, "multiplies two number values")
    bindWithDoc(scope, "/", DividedByFunction, "divides <a> by <b>")
    bindWithDoc(scope, "÷", DividedByFunction, "divides <a> by <b>")
    bindWithDoc(scope, "^", PowerToFunction, "raise <a> to the <b>th power")
    bindWithDoc(scope, "neg", NegativeFunction, "return the negative of a number")
    bindWithDoc(scope, "min", MinFunction, "returns the smallest number")
    bindWithDoc(scope, "max", MaxFunction, "returns the largest number")
    bindWithDoc(scope, "abs", AbsFunction, "returns the absolute of the number")
    // @Formatter:on
}
