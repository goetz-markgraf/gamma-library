package de.gma.gamma.datatypes

import de.gma.gamma.builtins.checkForListOfPairs
import de.gma.gamma.builtins.createMapFromListOfPair
import de.gma.gamma.builtins.namespaces.special.PropertyFunction
import de.gma.gamma.datatypes.functions.FunctionValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.record.RecordValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.datatypes.values.*
import de.gma.gamma.parser.EvaluationException
import de.gma.gamma.parser.Position

abstract class AbstractValue(
    val sourceName: String,
    val beginPos: Position,
    val endPos: Position
) : Value {
    abstract override fun prettyPrint(): String

    override fun toString() = prettyPrint()

    override fun evaluate(scope: Scope): Value = this

    override fun prepare(scope: Scope): Value = this

    override fun toBoolean(): BooleanValue =
        when (this) {
            is BooleanValue -> this
            is IntegerValue -> BooleanValue.build(this.longValue != 0L)
            is FloatValue -> BooleanValue.build(this.doubleValue != 0.0)
            is StringValue -> BooleanValue.build(this.strValue.isNotEmpty())
            is VoidValue -> BooleanValue.build(false)
            else -> BooleanValue.build(true)
        }

    override fun toList(): ListValue =
        when (this) {
            is ListValue -> this
            is VoidValue -> ListValue.build(emptyList())
            is StringValue -> if (this.size() == 0) ListValue.buildEmpty() else ListValue.build(listOf(this))
            is RecordValue -> this.convertToList()
            else -> ListValue.build(listOf(this))
        }

    override fun toFloat(): FloatValue =
        when (this) {
            is FloatValue -> this
            is IntegerValue -> FloatValue.build(this.longValue.toDouble())
            is StringValue -> {
                val v = this.strValue.toDoubleOrNull()
                if (v == null) throw createException("$this is not an float value")
                else FloatValue.build(v)
            }

            else -> throw createException("$this cannot be converted to float")
        }

    override fun toStringValue(): StringValue =
        when (this) {
            is StringValue -> this
            else -> StringValue.build(this.prettyPrint())
        }

    override fun toInteger(): IntegerValue =
        when (this) {
            is IntegerValue -> this
            is StringValue -> {
                val v = this.strValue.toLongOrNull()
                if (v == null) throw createException("$this is not an integer value")
                else IntegerValue.build(v)
            }

            else -> throw createException("$this is not an integer value")
        }

    override fun toProperty(): PropertyValue =
        when (this) {
            is PropertyValue -> this
            is StringValue -> PropertyValue.build(this.strValue)
            else -> PropertyValue.build(this.toStringValue().strValue)
        }

    override fun toFunction(): FunctionValue =
        when (this) {
            is FunctionValue -> this
            is PropertyValue -> PropertyFunction(this)
            else -> throw createException("$this is not a function")
        }

    override fun toRecord(scope: Scope): RecordValue =
        when {
            this is RecordValue -> this
            this is ListValue && this.size() == 0 -> RecordValue.buildEmpty()
            this is ListValue -> {
                val checkedValue = checkForListOfPairs(this)
                RecordValue.build(createMapFromListOfPair(checkedValue, scope))
            }

            else -> throw createException("$this is not a record")
        }

    fun createException(message: String) =
        EvaluationException(message, sourceName, beginPos.line, beginPos.col)

}
