package de.gma.gamma

import de.gma.gamma.interpreter.MapScope
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Parser

fun main() {
    var cmd: String

    val buffer = StringBuffer()
    var lastBuffer = ""

    printHelp()

    while (true) {

        print("gamma> ")
        val inp = readLine() ?: break

        if (inp == "?" || inp == ".help" || inp == "help") {
            printHelp()
        } else if (inp == "." && buffer.isNotEmpty()) {
            lastBuffer = buffer.toString()
            execute(lastBuffer)
            buffer.delete(0, buffer.length)
        } else if (inp.startsWith(".")) {
            cmd = inp.drop(1)
            when (cmd) {
                "exit", "quit", "q" -> break

                "clear", "c" -> buffer.delete(0, buffer.length)

                "load", "l" -> {
                    buffer.delete(0, buffer.length)
                    buffer.append(lastBuffer)
                    println(lastBuffer)
                }
            }
        } else {
            buffer.append(inp).append('\n')
        }

    }

    println("Bye")
}

private fun printHelp() {
    println("Gamma Interpreter REPL")
    println("Commands")
    println("   .      -> executes and clears the code buffer")
    println("   .clear -> clears the code buffer")
    println("   .load  -> load code buffer with last executed code")
    println("   .exit  -> exits the REPL")
    println("   .quit  -> exits the REPL")
    println("   .help  -> display this help")
}


private fun execute(code: String) {
    println(code)
    val scope = MapScope()

    try {
        val parser = Parser(code, "Script")
        var expr = parser.nextExpression(-1)
        while (expr != null) {
            expr.evaluate(scope)
            expr = parser.nextExpression(-1)
        }

        println()
    } catch (l: EvaluationException) {
        println()
        println("*** Exception while evaluation code:\n${l::class.java} '${l.message}' in ${l.source}, line: ${l.line}, col: ${l.col}")
    }
}
