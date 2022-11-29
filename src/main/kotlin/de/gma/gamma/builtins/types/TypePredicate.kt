package de.gma.gamma.builtins.types

import de.gma.gamma.builtins.BuiltinFunction
import de.gma.gamma.datatypes.DataType
import de.gma.gamma.datatypes.DataType.*
import de.gma.gamma.datatypes.StringValue
import de.gma.gamma.datatypes.Value
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.ModuleScope
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.*

class TypePredicate(private val type: DataType) : BuiltinFunction(listOf("obj")) {
    override fun callInternal(scope: Scope, callParams: List<Value>): Value {
        val l = callParams[0].evaluate(scope)

        val fits = when (type) {
            BOOLEAN -> l is BooleanValue
            LIST -> l is ListValue
            PAIR -> l is PairValue
            FLOAT -> l is FloatValue
            INTEGER -> l is IntegerValue
            STRING -> l is StringValue
            FUNCTION -> l is FunctionValue
            RECORD -> l is RecordValue
            PROPERTY -> l is PropertyValue
            VOID -> l is VoidValue
            MODULE -> l is ModuleScope
        }

        return BooleanValue.build(fits)
    }
}
