package de.gma.gamma.interpreter

import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value

interface Scope {
    fun getValue(id: String): Value
    fun bind(name: String, value: Value, documentation: Remark? = null)
}
