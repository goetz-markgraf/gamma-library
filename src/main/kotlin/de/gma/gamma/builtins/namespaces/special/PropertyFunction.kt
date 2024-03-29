package de.gma.gamma.builtins.namespaces.special

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Namespace
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.PropertyValue

class PropertyFunction(val name: PropertyValue) : BuiltinFunction(listOf("obj")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val obj = callParams[0].evaluate(scope) as Namespace

        val posStr = name.identifier

        return obj.getValueForName(posStr, strict = true)
    }
}
