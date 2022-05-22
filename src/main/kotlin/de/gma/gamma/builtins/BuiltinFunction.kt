package de.gma.gamma.builtins

import de.gma.gamma.datatypes.functions.AbstractFunction

abstract class BuiltinFunction(params: List<String>) : AbstractFunction(builtInSource, nullPos, nullPos, params) {
    override fun prettyPrint(): String = "<builtin>"
    
}
