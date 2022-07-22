package de.gma.gamma.datatypes.scope

import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.AbstractValue
import de.gma.gamma.datatypes.Remark
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.values.EmptyValue
import de.gma.gamma.parser.EvaluationException

open class ModuleScope(sourceName: String, override val parent: Scope? = GammaBaseScope) : Scope,
    AbstractValue(sourceName, nullPos, nullPos) {
    private val content: MutableMap<String, Value> = mutableMapOf()
    private val remarks: MutableMap<String, Remark> = mutableMapOf()

    override fun toString(): String {
        return sourceName + ": " + content.keys.toList().toString()
    }

    override fun prettyPrint() =
        toString()

    fun getAllNames() =
        content.keys.toList()

    fun getRemark(name: String) =
        remarks[name]

    override fun getValue(id: String, strict: Boolean): Value {

        return content[id]
            ?: if (parent != null)
                parent!!.getValue(id, strict)
            else
                if (strict)
                    throw EvaluationException("id $id is undefined.")
                else
                    EmptyValue.build()
    }

    override fun containsLocally(id: String) =
        content.containsKey(id)

    override fun bindValue(name: String, value: Value, documentation: Remark?) {
        if (content.contains(name))
            throw ScopeException("Id $name is already defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }

    override fun setValue(name: String, value: Value, documentation: Remark?, strict: Boolean) {
        if (strict && !content.contains(name))
            throw ScopeException("Id $name is not defined.")

        content[name] = value
        if (documentation != null)
            remarks[name] = documentation
    }
}
