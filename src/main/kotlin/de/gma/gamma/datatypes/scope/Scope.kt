package de.gma.gamma.datatypes.scope

import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value

interface Scope : Namespace {

    fun containsLocally(id: String): Boolean

    val parent: Scope?

    fun bind(name: String, value: Value, documentation: Remark? = null)

    fun set(name: String, value: Value, documentation: Remark? = null)
}
