package de.gma.gamma.datatypes

import de.gma.gamma.datatypes.values.PairValue
import de.gma.gamma.datatypes.values.PropertyValue
import de.gma.gamma.datatypes.list.ListValue
import de.gma.gamma.datatypes.scope.Scope
import de.gma.gamma.parser.GammaException

fun checkForListOfPairs(list: ListValue) =
    list.allItems().map {
        if (it is PairValue)
            it
        else
            throw GammaException("Wrong Parameter, not list of pairs")
    }

fun isRecordDefinition(list: List<Value>) =
    list.isNotEmpty() && list.all { item ->
        item is PairValue && (item.first() is PropertyValue || item.first() is StringValue || item.first() is Identifier)
    }

fun createMapFromListOfPair(content: List<PairValue>, scope: Scope) =
    buildMap<String, Value> {
        content.forEach {
            val pair = it.evaluate(scope)
            if (pair.first() is PropertyValue)
                put((pair.first() as PropertyValue).identifier, pair.last())
            else
                put(pair.first().toStringValue().strValue, pair.last())
        }
    }
