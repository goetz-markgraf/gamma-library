package de.gma.gamma.interpreter

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value

open class MapScope(val parent: Scope? = GammaBaseScope()) : Scope {
    private val content: MutableMap<String, Value> = mutableMapOf()
    private val remarks: MutableMap<String, Remark> = mutableMapOf()

    override fun getValue(id: String): Value {
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

    override fun bind(name: String, value: Value, documentation: Remark?) {
        if (content.contains(name))
            throw ScopeException("Id $name is already defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }
}
