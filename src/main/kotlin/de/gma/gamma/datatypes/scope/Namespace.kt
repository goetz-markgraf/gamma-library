package de.gma.gamma.datatypes.scope

import de.gma.gamma.datatypes.Value

interface Namespace {

    fun getValueForName(id: String, strict: Boolean = true): Value

    fun containsNameLocally(id: String): Boolean

}
