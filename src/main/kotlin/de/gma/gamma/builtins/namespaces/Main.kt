package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateNamespace(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "at", GetAtFunction, "at <pos> <list> – returns the <pos>th element of this list")
    bindWithDoc(scope, "at*", GetAtStarFunction, "at <pos> <list> – returns the <pos>th element of this list or () if not existing")
    bindWithDoc(scope, "record", RecordFunction, "record <list of pair> – creates a record from a list of pairs")
    bindWithDoc(scope, "r*", RecordFunction, "r* <list of pair> – creates a record from a list of pairs")
    bindWithDoc(scope, "copy-with", CopyWithFunction, "copy-with <list of pair> <record> – creates a new record based on <record> with changes from a list of pairs")
    bindWithDoc(scope, "get-properties", GetPropertiesFunction, "get-properties <record> – returns all keys in this record as a list of strings")
    // @Formatter:on
}
