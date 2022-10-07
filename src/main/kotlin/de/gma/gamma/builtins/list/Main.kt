package de.gma.gamma.builtins.list

import de.gma.gamma.builtins.bindWithDoc
import de.gma.gamma.datatypes.scope.Scope

fun populateList(scope: Scope) {
    // @Formatter:off
    bindWithDoc(scope, "first", FirstFunction, "returns the first element or () if empty list")
    bindWithDoc(scope, "second", SecondFunction, "returns the second element or () if there is none")
    bindWithDoc(scope, "head", FirstFunction, "returns the first element or () if empty list")
    bindWithDoc(scope, "tail", TailFunction, "returns all but the fist element or empty list if empty list")
    bindWithDoc(scope, "last", LastFunction, "returns the last element or () if empty list")
    bindWithDoc(scope, "drop-last", DropLastFunction, "returns all but the last element or empty list if empty list")
    bindWithDoc(scope, "slice", SliceFunction, "returns a sublist")
    bindWithDoc(scope, "size", SizeFunction,  "returns the number of elements in this list")
    bindWithDoc(scope, "append", AppendFunction, "appends an item to the end of the list")
    bindWithDoc(scope, "distinct", DistinctFunction, "creates a new list without duplicates")
    bindWithDoc(scope, "sort", SortFunction, "creates a new list that is sorted numerically. Strings are converted to numbers, lists are sorted according to their length.")
    bindWithDoc(scope, "sort-desc", SortDescendingFunction, "creates a new list that is sorted numerically. Strings are converted to numbers, lists are sorted according to their length.")
    bindWithDoc(scope, "appendAll", AppendAllFunction, "appends all items of [new-list] the end of [list]")
    bindWithDoc(scope, "::", InsertFirstFunction, "prepends a list with an as the new first element")
    bindWithDoc(scope, "@", ConcatFunction, "concats [list-1] and [list-2]")

    bindWithDoc(scope, "list-generator", ListGeneratorFunction, "creates a list of size [size] that uses [function] to generate the values")
    bindWithDoc(scope, "repeat", RepeatFunction, "create a list from 0 to [size] - 1")
    bindWithDoc(scope, "..", RangeFunction, "create a list from [from] to [to] inclusive")
    bindWithDoc(scope, "map", MapFunction, "creates a new list with every item of [list] run through [function]")
    bindWithDoc(scope, "map*", MapStarFunction, "creates a new list with every item of [list] run through [function] with [index] value")
    bindWithDoc(scope, "flat-map", FlatMapFunction, "creates a new list with every item of [list] run through [function] flattened out")
    bindWithDoc(scope, "flat-map*", FlatMapStarFunction, "creates a new list with every item of [list] run through [function] flattened out")
    bindWithDoc(scope, "for-each", ForEachFunction, "calls [function] for every item of [list]")
    bindWithDoc(scope, "fold", FoldFunction, "folds the [list] with [function] and [initial] acc")
    bindWithDoc(scope, "reduce", ReduceFunction, "reduces the [list] with [function]")
    bindWithDoc(scope, "filter", FilterFunction, "filters the [list] such that it contains only elements the match the [predicate]")
    bindWithDoc(scope, "find", FindFunction, "find the first element in [list] where the [predicate] is true")
    bindWithDoc(scope, "zip", ZipFunction, "Creates a new list consisting of pairs of the curresponding elements from [list-1] and [list-2]")
    bindWithDoc(scope, "to-char-list", ToCharListFunction, "creates a list of characters from the [string]")

    bindWithDoc(scope, "contains?", ContainsPredicate, "tests if [item] is in [list]. This also includes strings.")
    bindWithDoc(scope, "does-not-contain?", DoesNotContainPredicate, "tests if [item] is not in [list]. This also includes strings.")
    bindWithDoc(scope, "is-empty?", EmptyPredicate, "tests if [list or string] is empty, i. e. does not contain elements or characters")
    bindWithDoc(scope, "is-not-empty?", NotEmptyPredicate, "tests if [list or string] contains at least one element or character")
    // @Formatter:on
}
