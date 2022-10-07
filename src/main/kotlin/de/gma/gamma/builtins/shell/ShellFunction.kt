package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue
import java.io.IOException
import java.util.concurrent.TimeUnit

object ShellFunction : BuiltinFunction(listOf("cmd")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {

        val list = when (val p = callParams[0].evaluate(scope)) {
            is ListValue -> p
            is StringValue -> ListValue.build(listOf(p))
            else -> p.toList()
        }

        return try {
            val parts = list.allItems().map { it.toStringValue().strValue }
            val proc = ProcessBuilder(*parts.toTypedArray()).start()

            proc.waitFor(60, TimeUnit.SECONDS)
            val ret = proc.inputStream.bufferedReader().readText()

            ListValue.build(ret.split("\n").map { StringValue.build(it) })
        } catch (e: IOException) {
            e.printStackTrace()
            VoidValue.build()
        }
    }
}
