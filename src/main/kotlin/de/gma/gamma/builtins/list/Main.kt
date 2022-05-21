package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateList(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "first", FirstFunction(), "first <list> – returns the first element or () if empty list")
    bindWithDoc(scope, "last", LastFunction(), "last <list> – returns the last element or () if empty list")
    bindWithDoc(scope, "tail", TailFunction(), "tail <list> – returns all but the fist element or empty list if empty list")
    bindWithDoc(scope, "size", SizeFunction(), "size <list> – returns the number of elements in this list")
    bindWithDoc(scope, "get-at", GetAtFunction(), "get-at <pos> <list> – returns the <pos>th element of this list")
    bindWithDoc(scope, "append", AppendFunction(), "append <item> <list> – appends an item to the end of the list")
    bindWithDoc(scope, "::", InsertFunction(), "<item> :: <list> – prepends a list with an as the new first element")
    // @Formatter:on
}
