package de.gma.gamma.parser

import de.gma.gamma.datatypes.GValue

open class BaseParserTest {
    private lateinit var parser: Parser


    protected fun getExpression(source: String): GValue? {
        parser = Parser(source, "Script")
        return parser.nextExpression(-1)
    }
}
