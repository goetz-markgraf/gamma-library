package de.gma.gamma.builtins

import de.gma.gamma.datatypes.direct.GRemark
import de.gma.gamma.datatypes.functions.AbstractFunction
import de.gma.gamma.interpreter.Scope
import de.gma.gamma.parser.Position

const val builtInSource = "<builtin>"

val nullPos = Position(0, 0, 0)

fun bindWithDoc(scope: Scope, id: String, func: AbstractFunction, doc: String) {
    scope.bind(id, func, GRemark.buildDoc(doc))
}
