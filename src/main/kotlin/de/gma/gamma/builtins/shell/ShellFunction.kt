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


val isWindows = System.getProperty("os.name")
    .lowercase(Locale.getDefault()).startsWith("windows")


// SECURITY: The command string is passed directly to the shell interpreter (sh -c on Unix,
// cmd.exe /c on Windows). Any Gamma argument value becomes a shell command fragment with full
// access to the host system. This is intentional for scripting use, but means that untrusted
// Gamma code can execute arbitrary shell commands, read/write files, and exfiltrate data.
// Do not embed this interpreter in untrusted or multi-tenant contexts without disabling or
// sandboxing the `shell` builtin.
object ShellFunction : BuiltinFunction("shell", listOf("cmd")) {
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
        val cwd = GammaBaseScope.from(scope).getValueForName(CWD_NAME).toStringValue().strValue
        builder.directory(File(cwd))
        val process = builder
            .redirectErrorStream(true)
            .start()
        // Read stdout (which also includes stderr via redirectErrorStream) BEFORE waitFor().
        // The process may block on writing if the pipe buffer fills up (~64 KB), so we must
        // drain the stream first, then wait for the process to exit.
        val ret = process.inputStream.bufferedReader().readText()
        val exitCode = process.waitFor()

        return if (exitCode != 0) {
            IntegerValue.build(exitCode.toLong())
        } else {
            ListValue.build(ret.split("\n").filter { it.trim().isNotEmpty() }.map { StringValue.build(it) })
        }
    }
}


