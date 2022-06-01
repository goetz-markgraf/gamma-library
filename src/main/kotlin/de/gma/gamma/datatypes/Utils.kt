package de.gma.gamma.builtins

import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.Position

const val builtInSource = "<builtin>"

val nullPos = Position(0, 0, 0)

fun bindWithDoc(scope: Scope, id: String, func: FunctionValue, doc: String) {
    scope.bind(id, func, Remark.buildDoc(doc))
}
