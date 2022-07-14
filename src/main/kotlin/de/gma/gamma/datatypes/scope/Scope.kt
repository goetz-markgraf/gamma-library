package de.gma.gamma.datatypes.scope

import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value

interface Scope : Namespace {

    val parent: Scope?

    fun bindValue(name: String, value: Value, documentation: Remark? = null)

    fun setValue(name: String, value: Value, documentation: Remark? = null, strict: Boolean = true)
}
