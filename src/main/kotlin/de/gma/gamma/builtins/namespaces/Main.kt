package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateNamespace(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "at", GetAtFunction, "returns the [pos]th element of this list")
    bindWithDoc(scope, "at*", GetAtStarFunction, "returns the [pos]th element of this list or () if not existing")
    bindWithDoc(scope, "copy-with", CopyWithFunction, "creates a new record based on [record] with changes from a list of pairs")
    bindWithDoc(scope, "get-properties", GetPropertiesFunction, "returns all keys in this record as a list of strings")
    // @Formatter:on
}
