package de.gma.gamma.builtins.namespaces

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateNamespace(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "at", GetAtFunction(), "at <pos> <list> – returns the <pos>th element of this list")
    bindWithDoc(scope, "record", RecordFunction(), "record <list of pair> – creates a record from a list of pairs")
    bindWithDoc(scope, "copy-with", CopyWithFunction(), "copy <list of pair> <record> – creates a new record based on <record> with changes from a list of pairs")
    // @Formatter:on
}