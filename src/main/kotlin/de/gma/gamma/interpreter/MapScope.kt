package de.gma.gamma.interpreter

import de.gma.gamma.datatypes.GValue
import de.gma.gamma.datatypes.direct.GRemark

class MapScope(val parent: Scope?) : Scope {
    private val content: MutableMap<String, GValue> = mutableMapOf()
    private val remarks: MutableMap<String, GRemark> = mutableMapOf()

    override fun getValue(id: String): GValue {
        val ret = content[id]

        if (ret == null) {
            if (parent != null)
                return parent.getValue(id)
            else
                throw ScopeException("Id $id not found in scope.")
        } else {
            return ret
        }
    }

    override fun bind(name: String, value: GValue, documentation: GRemark?) {
        if (content.contains(name))
            throw ScopeException("Id $name is already defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }
}
