package de.gma.gamma.builtins._gamma

val codeFunctional = """
'combines two functions to a new one that applies both functions after another'
let (**) f g =
    [ v : v ▷ f ▷ g]

'pipes the [value] on the right into the [function] on the left'
let (<|) f v =
    f v

'pipes the [value] on the right into the [function] on the left'
let (◁) = (<|)

'converts a list of 2-item-lists to a list of pairs'
let make-pair-list list = list ▷ map make-pair

'checks each [predicate] called with [value] to evaluate to first expression with a true predicate'
let when* v list =
    when {
        is-empty? list → ()
        is-boolean? list.first.first ∧ list.first.first → list.first.last
        list.first.first v → list.first.last
        else → when* v list.tail
    }

'returns the given parameter as is'
let id a = a

'returns the first value that is not falsey'
let or expression-list =
    when {
        is-empty? expression-list → ()
        expression-list.first → expression-list.first
        else → or expression-list.tail
    }

""".trimIndent()
