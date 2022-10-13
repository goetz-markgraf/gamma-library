package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import java.io.File


object CDFunction : BuiltinFunction(listOf("dir")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val path = callParams[0].toStringValue().strValue

        val cwd = (GammaBaseScope.getValueForName(CWD_NAME) as StringValue).strValue
        val newCwd = StringValue.build(File(cwd, path).absolutePath)
        GammaBaseScope.bindValue(CWD_NAME, newCwd, null, false)

        return newCwd
    }

}
