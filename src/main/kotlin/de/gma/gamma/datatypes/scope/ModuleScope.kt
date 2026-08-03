package de.gma.gamma.datatypes.scope

import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.parser.GammaException
import de.gma.gamma.parser.isStartOfIdentifier

open class ModuleScope(
    sourceName: String,
    final override val parent: Scope?
) : Scope, AbstractValue(sourceName, nullPos, nullPos) {

    protected val content: MutableMap<String, Value> = mutableMapOf()
    protected val remarks: MutableMap<String, Remark> = mutableMapOf()

    init {
        content["this"] = this
        if (parent != null && parent is ModuleScope)
            content["super"] = parent
    }

    override fun toString(): String {
        return sourceName + ": " + content.keys.toList().toString()
    }

    override fun prettyPrint() =
        toString()

    fun getAllNames() =
        content.keys.toList()

    fun getRemark(name: String): String {
        val rem = remarks[name]?.strValue?.trim() ?: return ""
        val value = content[name]

        if (value is FunctionValue) {
            return if (isStartOfIdentifier(name.first()) || value.paramNames.size != 2)
                "$name ${value.paramNames.joinToString(" ")} – $rem"
            else
                "${value.paramNames.first()} $name ${value.paramNames.last()} – $rem"
        }

        return "$name – $rem"
    }

    override fun getValueForName(id: String, strict: Boolean): Value {
        // Iterative traversal of the ModuleScope chain to avoid stack overflow
        // for deeply nested scopes (many nested lambda calls).
        var scope: ModuleScope? = this
        while (scope != null) {
            scope.content[id]?.let { return it }
            scope = scope.parent as? ModuleScope
        }
        // Fell off the ModuleScope chain — delegate to the root non-ModuleScope parent (e.g. GammaBaseScope)
        return parent?.getValueForName(id, strict)
            ?: if (strict) throw GammaException("id $id is undefined.")
            else VoidValue.build()
    }

    override fun containsNameLocally(id: String) =
        content.containsKey(id)

    override fun bindValue(name: String, value: Value, documentation: Remark?, strict: Boolean) {
        if (strict && content.contains(name))
            throw ScopeException("Id $name is already defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
        else
            remarks.remove(name)
    }

    override fun setValue(name: String, value: Value, documentation: Remark?, strict: Boolean) {
        if (strict && !content.contains(name))
            throw ScopeException("Id $name is not defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }
}
