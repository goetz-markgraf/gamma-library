package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.CWD_NAME
import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.EvaluationException
import java.io.File


object CDFunction : BuiltinFunction(listOf("dir")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val path = callParams[0].toStringValue().strValue

        val baseScope =
            getBaseScope(scope) ?: throw EvaluationException("cannot call this function on a non module scope")

        val cwd = (baseScope.getValueForName(CWD_NAME) as StringValue).strValue
        val newCwd = StringValue.build(File(cwd, path).absolutePath)
        baseScope.bindValue(CWD_NAME, newCwd, null, false)

        return newCwd
    }

    private fun getBaseScope(scope: Scope): GammaBaseScope? {
        var currScope: Scope? = scope
        while (currScope != null && currScope is ModuleScope && currScope != GammaBaseScope)
            currScope = currScope.parent

        return if (currScope !is GammaBaseScope)
            null
        else
            currScope
    }
}
