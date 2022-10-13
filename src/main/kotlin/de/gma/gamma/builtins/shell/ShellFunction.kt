package de.gma.gamma.builtins.shell

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.builtins.GammaBaseScope
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.IntegerValue
import java.io.*
import java.util.*
import java.util.concurrent.Executors


val isWindows = System.getProperty("os.name")
    .lowercase(Locale.getDefault()).startsWith("windows")


object ShellFunction : BuiltinFunction(listOf("cmd")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {

        val list = when (val p = callParams[0].evaluate(scope)) {
            is ListValue -> p
            is StringValue -> ListValue.build(listOf(p))
            else -> p.toList()
        }

        val cmd = list.allItems().joinToString(" ") { it.toStringValue().strValue }

        val builder = ProcessBuilder()
        if (isWindows) {
            builder.command("cmd.exe", "/c", cmd)
        } else {
            builder.command("sh", "-c", cmd)
        }
        val cwd = GammaBaseScope.getValueForName(CWD_NAME).toStringValue().strValue
        builder.directory(File(cwd))
        val process = builder.start()
        val streamGobbler = StreamGobbler(process.inputStream)
        val future = Executors.newSingleThreadExecutor().submit(streamGobbler)

        val exitCode = process.waitFor()

        return if (exitCode != 0) {
            IntegerValue.build(exitCode.toLong())
        } else {
            future.get()
            val ret = streamGobbler.getOutput()
            ListValue.build(ret.split("\n").filter { it.trim().isNotEmpty() }.map { StringValue.build(it) })
        }
    }
}

private class StreamGobbler(
    private val inputStream: InputStream,
) : Runnable {
    private val buf = StringWriter()
    override fun run() {
        BufferedReader(InputStreamReader(inputStream)).lines()
            .forEach(buf::appendLine)
    }

    fun getOutput() =
        buf.toString()
}
