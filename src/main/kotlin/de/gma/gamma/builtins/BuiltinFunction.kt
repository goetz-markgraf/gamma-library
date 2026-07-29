package de.gma.gamma.builtins

import de.gma.gamma.datatypes.functions.FunctionValue

abstract class BuiltinFunction(private val name: String, params: List<String>) : FunctionValue(builtInSource, nullPos, nullPos, params) {
    override fun prettyPrint(): String = "[builtin: $name]"

}
