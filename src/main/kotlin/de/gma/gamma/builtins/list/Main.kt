package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.builtins.list.predicates.*
import de.gma.gamma.datatypes.scope.Scope

fun populateList(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "first", FirstFunction, "first <list> – returns the first element or () if empty list")
    bindWithDoc(scope, "second", SecondFunction, "second <list> – returns the second element or () if there is none")
    bindWithDoc(scope, "head", FirstFunction, "head <list> – returns the first element or () if empty list")
    bindWithDoc(scope, "tail", TailFunction, "tail <list> – returns all but the fist element or empty list if empty list")
    bindWithDoc(scope, "last", LastFunction, "last <list> – returns the last element or () if empty list")
    bindWithDoc(scope, "drop-last", DropLastFunction, "drop-last <list> – returns all but the last element or empty list if empty list")
    bindWithDoc(scope, "slice", SliceFunction, "slice <from> <length> <list> – returns a sublist")
    bindWithDoc(scope, "size", SizeFunction, "size <list> – returns the number of elements in this list")
    bindWithDoc(scope, "append", AppendFunction, "append <item> <list> – appends an item to the end of the list")
    bindWithDoc(scope, "appendAll", AppendAllFunction, "appendAll <new-list> <list> – appends all items of <new-list> the end of <list>")
    bindWithDoc(scope, "::", InsertFirstFunction, "<item> :: <list> – prepends a list with an as the new first element")
    bindWithDoc(scope, "@", ConcatFunction, "<list-1> @ <list-2> – concats <list-1> and <list-2>")

    bindWithDoc(scope, "list-generator", ListGeneratorFunction, "list-generator <size> <function> – creates a list of size <size> that uses <function> to generate the values")
    bindWithDoc(scope, "repeat", RepeatFunction, "repeat <num> - create a list from 0 to <repeate> - 1")
    bindWithDoc(scope, "..", RangeFunction, "<start> .. <end> - create a list from start> to <end> inclusive")
    bindWithDoc(scope, "map", MapFunction, "map <function> <list> – creates a new list with every item of <list> run through <function>")
    bindWithDoc(scope, "map*", MapStarFunction, "map* <function> <list> – creates a new list with every item of <list> run through <function> with <index> value")
    bindWithDoc(scope, "for-each", ForEachFunction, "for-each <function> <list> – calls <function> for every item of <list>")
    bindWithDoc(scope, "fold", FoldFunction, "fold <inital> <function> <list> – folds the <list> with <function> and <initial> acc")
    bindWithDoc(scope, "reduce", ReduceFunction, "reduce <function> <list> – reduces the <list> with <function>")
    bindWithDoc(scope, "filter", FilterFunction, "filter <predicate> <list> – filters the <list> such that it contains only elements the match the <predicate>")
    bindWithDoc(scope, "zip", ZipFunction, "zip <list-1> <list-2> – Creates a new list consisting of pairs of the curresponding elements from <list-1> and <list-2>")

    bindWithDoc(scope, "contains?", ContainsPredicate, "contains? <item> <list> – tests if <item> is in <list>. This also includes strings.")
    bindWithDoc(scope, "is-list?", ListPredicate, "is-list? <value> – tests if <value> is a list. This also includes strings")
    bindWithDoc(scope, "is-string?", StringPredicate, "is-string? <value> – tests if <value> is a string")
    bindWithDoc(scope, "is-pair?", PairPredicate, "is-pair? <value> – tests if <value> is a pair, i. e. a list with two elements")
    bindWithDoc(scope, "is-empty?", EmptyPredicate, "is-empty? <list or string> – tests if <list or string> is empty, i. e. does not contain elements or characters")
    bindWithDoc(scope, "is-not-empty?", NotEmptyPredicate, "is-not-empty? <list or string> – tests if <list or string> contains at least one element or character")
    // @Formatter:on
}
