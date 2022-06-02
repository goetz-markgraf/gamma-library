package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.builtins.list.predicates.*
import de.gma.gamma.datatypes.scope.Scope

fun populateList(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "first", FirstFunction(), "first <list> – returns the first element or () if empty list")
    bindWithDoc(scope, "second", FirstFunction(), "second <list> – returns the second element or () if there is none")
    bindWithDoc(scope, "last", LastFunction(), "last <list> – returns the last element or () if empty list")
    bindWithDoc(scope, "tail", TailFunction(), "tail <list> – returns all but the fist element or empty list if empty list")
    bindWithDoc(scope, "drop-last", DropLastFunction(), "drop-last <list> – returns all but the last element or empty list if empty list")
    bindWithDoc(scope, "slice", SliceFunction(), "slice <from> <length> <list> – returns a sublist")
    bindWithDoc(scope, "size", SizeFunction(), "size <list> – returns the number of elements in this list")
    bindWithDoc(scope, "at", AtFunction(), "at <pos> <list> – returns the <pos>th element of this list")
    bindWithDoc(scope, "append", AppendFunction(), "append <item> <list> – appends an item to the end of the list")
    bindWithDoc(scope, "::", InsertFunction(), "<item> :: <list> – prepends a list with an as the new first element")

    bindWithDoc(scope, "list-generator", ListGeneratorFunction(), "list-generator <size> <function> – creates a list of size <size> that uses <function> to generate the values")
    bindWithDoc(scope, "map", MapFunction(), "map <function> <list> – creates a new list with every item of <list> run through <function>")

    bindWithDoc(scope, "is-list?", ListPredicate(), "is-list? <value> – tests if <value> is a list. This also includes strings")
    bindWithDoc(scope, "is-string?", StringPredicate(), "is-string? <value> – tests if <value> is a string")
    bindWithDoc(scope, "is-pair?", PairPredicate(), "is-pair? <value> – tests if <value> is a pair, i. e. a list with two elements")
    bindWithDoc(scope, "is-empty?", EmptyPredicate(), "is-empty? <list or string> – tests if <list or string> is empty, i. e. does not contain elements or characters")
    bindWithDoc(scope, "is-not-empty?", NotEmptyPredicate(), "is-not-empty? <list or string> – tests if <list or string> contains at least one element or character")
    // @Formatter:on
}
