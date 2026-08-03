package de.gma.gamma.builtins.io

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import java.io.File
import java.io.IOException

object ReadLinesFunction : BuiltinFunction("read-lines", listOf("filename")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val filename = callParams[0].evaluate(scope).toStringValue()

        val file = File(filename.strValue)

        if (file.exists() && !file.isDirectory && file.canRead()) {
            try {
                return ListValue.build(file.readLines().map { StringValue.build(it) })
            } catch (e: IOException) {
                throw createException("Cannot read file: ${file.path}")
            }
        } else {
            throw createException("file $filename is not readable")
        }
    }
}
