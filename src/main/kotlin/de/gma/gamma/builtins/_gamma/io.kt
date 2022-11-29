package de.gma.gamma.builtins._gamma

val codeIo = """
'prints the parameters and appends a newline'
let print v =
    let ret = print* v
    print* "\n"
    ret
""".trimIndent()
