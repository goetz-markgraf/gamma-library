package de.gma.gamma.builtins.types

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.DataType
import de.gma.gamma.datatypes.scope.Scope

fun populateTypes(scope: Scope) {
    DataType.values().forEach { type ->
        bindWithDoc(
            scope,
            "is-${type.name.lowercase()}?",
            TypePredicate(type),
            "checks if <obj> is of type ${type.name.lowercase()}"
        )
    }
    DataType.values().forEach { type ->
        bindWithDoc(
            scope,
            "can-be-used-as-${type.name.lowercase()}?",
            CanBeUsedAsPredicate(type),
            "checks if <obj> can be used as a type ${type.name.lowercase()}"
        )
    }
}
