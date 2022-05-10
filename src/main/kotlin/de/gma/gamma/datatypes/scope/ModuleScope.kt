package de.gma.gamma.datatypes.scope

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value

open class ModuleScope(override val parent: Scope? = GammaBaseScope()) : Scope {
    private val content: MutableMap<String, Value> = mutableMapOf()
    private val remarks: MutableMap<String, Remark> = mutableMapOf()

    override fun getValue(id: String): Value {

        return content[id]
            ?: if (parent != null)
                parent!!.getValue(id)
            else
                throw ScopeException("Id $id not found in scope.")
    }

    override fun containsLocally(id: String) =
        content.containsKey(id)

    override fun bind(name: String, value: Value, documentation: Remark?) {
        if (content.contains(name))
            throw ScopeException("Id $name is already defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }

    override fun set(name: String, value: Value, documentation: Remark?) {
        if (!content.contains(name))
            throw ScopeException("Id $name is not defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }
}
