package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.Scope
import java.io.File


object CDFunction : BuiltinFunction("cd", listOf("dir")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val path = callParams[0].toStringValue().strValue

        val cwd = (GammaBaseScope.getValueForName(CWD_NAME) as StringValue).strValue
        val newDir = File(cwd, path)
        if (!newDir.exists() || !newDir.isDirectory) {
            throw createException("Target directory does not exist: $path")
        }
        val newCwd = StringValue.build(newDir.absolutePath)
        GammaBaseScope.bindValue(CWD_NAME, newCwd, null, false)

        return newCwd
    }

}
