package de.gma.gamma.datatypes

import de.gma.gamma.builtins.builtInSource
import de.gma.gamma.builtins.nullPos
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.VoidValue
import de.gma.gamma.parser.Parser
import de.gma.gamma.parser.Position

class DebugValue(
    sourceName: String,
    beginPos: Position,
    endPos: Position,
) : AbstractValue(sourceName, beginPos, endPos) {

    private var _scope: Scope? = null

    override fun prettyPrint() = "(debug)"

    override fun evaluate(scope: Scope): Value {
        try {
            _scope = scope

            // set breakpoint in next line to debug
            return VoidValue.build()
        } finally {
            _scope = null
        }
    }

    private fun show(id: String) =
        _scope?.getValueForName(id, false)?.evaluate(_scope!!)

    private fun run(code: String): Value? {
        if (_scope == null)
            return null

        val p = Parser(code)
        val expr = p.nextExpression(-1)
        return expr?.evaluate(_scope!!)
    }

    companion object {
        fun build() = DebugValue(builtInSource, nullPos, nullPos)
    }
}
