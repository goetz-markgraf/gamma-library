package de.gma.gamma.interpreter

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.direct.GRemark

interface Scope {
    fun getValue(id: String): GValue
    fun bind(name: String, value: GValue, documentation: GRemark? = null)
}
