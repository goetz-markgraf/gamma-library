package de.gma.gamma.datatypes.scope

import de.gma.gamma.datatypes.Value

interface Namespace {

    fun getValue(id: String, strict: Boolean = true): Value

    fun containsLocally(id: String): Boolean

}
