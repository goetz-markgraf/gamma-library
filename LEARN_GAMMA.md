# Learn to program in _gamma_

_(Please keep in mind that _gamma_ is still work in progress, not free from errors and
will probably change a lot in the future)_

## Introduction

_gamma_ is an expression based functional language. That meens that everything in _gamma_ is an executable
expression and has a value. There are only very few keywords or language constructs, almost everything is
or can be done with writing and calling functions.

## Basic Syntax

_gamma_ uses indentation to structure blocks if they are not already structures by parenthesis or brackets.

There are only a few special expressions:

```
    # bind a value (10) to a name ("a")
    let a = 10
  
    # binding a function with two parameters ("a" and "b") to a name ("add")
    let add a b =
        a + b
  
    # change the value bound to the name "mutable!" with a new value (20). Only names that end
    # with a "!" can be mutated
    set mutable! = 20
  
    # this is an "if" expression that evaluate the second or third expression, depending
    # on the result of the first expression (predicate)
    a > 2 ? "a is bigger that 2" : "a is smaller or equal to 2" 
```

Everything else is an normal expression.

Every Expression is either a literal value or a computed expression, mostly a function call.

## Value Literal

_gamma_ knows the following value literals

```
    # integer values (64 bit)
    12764
    -364
    0
  
    # float values (64 bit)
    10.5
    -3847.24
    0.0
  
    # boolean values
    true
    false
  
    # Strings
    "Hello World."
    "New\nLine"
  
    # the empty value
    ()

    # comments are literal expressions, too
    # all comments have an empty value (), but they do have a value!
    # comments come in two flawours:

    # line comments start with a '#' and end at the end of the line
    ' block comments can span more than one line
      and are terminated with single quotes'
```

Additionally there are two ways of constructing a list of values:

```
    # a pair is a list of two values
    10 -> 20
    10 → 20
  
    # a list is values between { and }
    {
        10
        20
        30
    }
  
    # if you want to place multiple expressions on one line, separate with "," or ";"
    # the tree following expressions produce identical results 
    {1, 2, 3}
    {1; 2; 3} 
    {
        1
        2
        3
    }
```

A list can hold any number of values of any type.

## string interpolation

Within a normal string you can add _gamma_expressions, enclosed in `$(...)`:

```
  let world = "World"
  
  # will result in "Hello, World!"
  "Hello, $(world)!" 
```

The interpolation can be suppressed if you write an escape "\" before the "$":

```
  # this results in a string with the content $()
  "\$()"
```

## calling functions

There are two kinds of function call in _gamma_.

### function calls by name

If a function is bound to a name, invoking the function is as easy as placing expressions as parameters behind the
function name:

```
    # calling the built-in function to print a value to the system console
    print "Hello, World."
  
    # getting the size (=length) of a list
    size {1, 2, 3}
  
    # getting the 2nd element of a list (lists are 0 based)
    at 1 {1, 2, 3)
```

#### partial application

If you supply not the needed amount of parameters, the expressions will produce a function that takes the
remaining parameters but has the first ones already "baked in":

```
    # create a function the adds two numbers together
    let add a b = a + b
  
    # create a partial applicated function the adds 10 to any other value
    let addTen = add 10
  
    # this will produce 30
    addTen 20
```

### operators

All operators (like '+', '-' etc.) are also function calls. They have some restrictions, though:

1. They always take two parameters
2. They cannot be partial applicated
3. The operator is written in between the two expressions that form the parameters

```
    # add two numbers
    10 + 20
  
    # add a number to the result of a function call
    (size {1, 2, 3}) + 10
  
    # since function calls bind higher than operator call, you can write also
    size {1, 2, 3} + 10 
```

## usage of operators as function names

If you want to use an operator like a normal function, you can place the operator between
parenthesis (without whitespace!) and place it at the beginning of the function call.

```
    # add two numbers
    (+) 10 20
```

You must also place these parenthesis around the operator if you want to create your own operator

```
    # create a "power two" function
    let (**) a b = a ^ b
  
    10 ** 20 
```

_(Of course, you don't need to do that, because `^` is already a function to raise a number to the nth power.)_

### pipe operator

The pipe operator (`|>`) can be used to enhance the readability of the code. It is defined like this

```
  let (|>) v f = f v
```

It take the value from **before** the operator and puts it into the function behind the
operator. That is why in _gamma_ the parameter indicating the value to work on comes last.

```
  # get the size of a list
  size list
  
  # this call produces the same result
  list |> size
  
  # the pipe operator lets to chain call together
  list
  |> size
  |> print
```

## lambda functions

As said before, functions are also values that can be assigned. Function can easily be
created "on the fly" (aka lambda function) using `[` and `]` and a `:` to seperate parameters from the code. Actually,
the
declaration of functions described above is only a shorthand way of assigning a lambda function
to a value

```
  let add a b = a + b
  
  # is the same as
  
  let add = [a b : a + b]
```

You can use lambdas for example in combination with the map function to work over the items of a list

```
  let list = {1, 2, 3}
  
  # add 10 to every item in the list
  # produces a new list {11, 12, 13}
  
  let result = list |> map [item : item + 10]
```

## record types

Apart from lists _gamma_ can create `record`-Types that match a value to a property name.

```
  # create a record as a list of pairs with propertys
  let a = {
    :name -> "Mueller"
    :first-name -> "Willi"
    :birthdate -> "01.01.1980"
  }
```

Accessing the record is possible via the property names that work like getter functions:

```
  # getting the name value of the record 'a'
  print (:name a)
  
  # alternate access with dot notation
  print (a.name)
```

Like all other data structures in _gamma_ a `record`-Type is immutable. You can create a modified
copy with the `copy-with`-function:

```
  # create a sibling of the person in a
  let b =
    a |> copy-with { :first-name -> "Sarah" } 
```

## type conversion

_gamma_ is a good choice to create small scripts. And with a lot of scripting languages, the typing
of objects is dynamic and not static. To support scripting, a lot of types are also converted
on the fly if needed

The following table shows the automatic type conversions. Some of these conversions are
happening implicitely. Every conversion can be forced with the `to-xxx`-functions.

| target type | conversion                                                                                                           |
|-------------|----------------------------------------------------------------------------------------------------------------------|
| string      | Any type will be converted to string if needed                                                                       |
| boolean     | Any type will be converted to a boolean. 0, 0.0, (), {}, "" are considered false, everything else is true            |
| list        | Any type will be converted to a list. () and "" will be an empty list, everything else will be one element in a list | 
| float       | An integer will be converted to a float. A string will be converted, if it contains a float written as a string      | 
| integer     | A string will be converted, if it contains an integer written as a string                                            |
| property    | a string will be converted to a property with the same "name"                                                        |
| function    | a property will be converted to a getter-function for a record                                                       |
| record      | any list that contains only pairs will be converted to a record                                                      |

# Support for shell scripts

_gamma_ can be used to create shell scripts. If the gamma executable is on the
`$PATH` you can create a _gamma_ script with this as the first line

```
#! gamma
```

### commands for shell interaction

the function `sh` which can be written shorter as `$` executes the parameter as a
shell script and returns the content as a list of strings. This can easily be processed via
_gamma_ functions to create powerfull shell scripts.

This gives you (on unix machines) the (first) process ID of a process with `gamma` in its name, if any is running,
or `{}`.

```
  $ "ps -ax"
  ▷ filter (contains? "gamma")
  ▷ first
  ▷ split
  ▷ first
```

# Built-in functions

These are the functions currently built in into _gamma_:

* is-boolean? obj – checks if [obj] is of type boolean
* is-list? obj – checks if [obj] is of type list
* is-float? obj – checks if [obj] is of type float
* is-integer? obj – checks if [obj] is of type integer
* is-string? obj – checks if [obj] is of type string
* is-function? obj – checks if [obj] is of type function
* is-record? obj – checks if [obj] is of type record
* is-property? obj – checks if [obj] is of type property
* is-void? obj – checks if [obj] is of type void
* is-module? obj – checks if [obj] is of type module
* can-be-used-as-boolean? obj – checks if [obj] can be used as a type boolean
* can-be-used-as-list? obj – checks if [obj] can be used as a type list
* can-be-used-as-float? obj – checks if [obj] can be used as a type float
* can-be-used-as-integer? obj – checks if [obj] can be used as a type integer
* can-be-used-as-string? obj – checks if [obj] can be used as a type string
* can-be-used-as-function? obj – checks if [obj] can be used as a type function
* can-be-used-as-record? obj – checks if [obj] can be used as a type record
* can-be-used-as-property? obj – checks if [obj] can be used as a type property
* can-be-used-as-void? obj – checks if [obj] can be used as a type void
* can-be-used-as-module? obj – checks if [obj] can be used as a type module
* to-boolean obj – converts [obj] to type boolean
* to-list obj – converts [obj] to type list
* to-float obj – converts [obj] to type float
* to-integer obj – converts [obj] to type integer
* to-string obj – converts [obj] to type string
* to-function obj – converts [obj] to type function
* to-record obj – converts [obj] to type record
* to-property obj – converts [obj] to type property
* to-void obj – converts [obj] to type void
* to-module obj – converts [obj] to type module
* v |> f – calls [function] with [value] as parameter
* v ▷ f – calls [function] with [value] as parameter
* v ⊳ f – calls [function] with [value] as parameter
* debug – evaluates the DebugValue for debug purposes
* break – evaluates the DebugValue for debug purposes
* print* value – prints a value to standard out without a newline at the end
* read-lines filename – reads the content of [filename] as a list of strings
* split string – splits a [string] separated by blank into a list of strings
* split-by separator string – splits a [string] separated by [separator] into a list of strings
* join list – joins all elements of [list] together as one string, seperated by a blank
* join-by string list – joins all elements of [list] together as one string, seperated by [string]
* a + b – adds two number values
* a - b – subtract [a] from [a]
* a * b – multiplies two number values
* a × b – multiplies two number values
* a / b – divides [a] by [a]
* a ÷ b – divides [a] by [a]
* a ^ b – raise [a] to the [a]th power
* neg a – return the negative of a number
* min list – returns the smallest number
* max list – returns the largest number
* abs value – returns the absolute of the number
* first list – returns the first element or () if empty list
* head list – returns the first element or () if empty list
* tail list – returns all but the fist element or empty list if empty list
* last list – returns the last element or () if empty list
* drop-last list – returns all but the last element or empty list if empty list
* slice from length list – returns a sublist
* size list – returns the number of elements in this list
* append item list – appends an item to the end of the list
* distinct list-1 – creates a new list without duplicates
* sort list – creates a new list that is sorted numerically. Strings are converted to numbers, lists are sorted
  according to their length.
* sort-desc list – creates a new list that is sorted numerically. Strings are converted to numbers, lists are sorted
  according to their length.
* appendAll item list – appends all items of [new-list] the end of [list]
* item :: list – prepends a list with an as the new first element
* list-1 @ list-2 – concatenates [list-1] and [list-2]
* list-generator size function – creates a list of size [size] that uses [function] to generate the values
* repeat size – create a list from 0 to [size] - 1
* from .. to – create a list from [from] to [to] inclusive
* map function list – creates a new list with every item of [list] run through [function]
* map* function list – creates a new list with every item of [list] run through [function] with [index] value
* flat-map function list – creates a new list with every item of [list] run through [function] flattened out
* flat-map* function list – creates a new list with every item of [list] run through [function] flattened out
* for-each function list – calls [function] for every item of [list]
* fold initial function list – folds the [list] with [function] and [initial] acc
* reduce function list – reduces the [list] with [function]
* filter predicate list – filters the [list] such that it contains only elements the match the [predicate]
* find predicate list – find the first element in [list] where the [predicate] is true
* zip list-1 list-2 – Creates a new list consisting of pairs of the corresponding elements from [list-1] and [list-2]
* make-pair list – creates a pair from a [list] of size 2
* make-char-list string – creates a list of characters from the [string]
* contains? predicate list – tests if [item] is in [list]. This also includes strings.
* does-not-contain? predicate list – tests if [item] is not in [list]. This also includes strings.
* is-empty? list – tests if [list or string] is empty, i. e. does not contain elements or characters
* is-not-empty? list – tests if [list or string] contains at least one element or character
* when cases – checks each predicate to evaluate to first expression with a true predicate
* a = b – checks if [a] is equal to [b]
* a != b – checks if [a] is not equal to [a]
* a ≠ b – checks if [a] is not equal to [a]
* a > b – checks if the first value is greater than the second
* a >= b – checks if the first value is greater than or equal to the second
* a ≥ b – checks if the first value is greater than or equal to the second
* a < b – checks if the first value is less than the second
* a <= b – checks if the first value is less than or equal to the second
* a ≤ b – checks if the first value is less than or equal to the second
* a & b – returns true if [a] and [a] are both true
* a ∧ b – returns true if [a] and [a] are both true
* a | b – returns true if either [a] or [a] or both are true
* a ∨ b – returns true if either [a] or [a] or both are true
* not a – returns true if [a] is false and false if [a] is true
* pipeline id list-of-expressions – executes each [expression], always using [id] for the result of the previous one
* else – equal to true, can be used in when expressions
* at pos list – returns the [pos]th element of this list
* at* pos list – returns the [pos]th element of this list or () if not existing
* copy-with property-list record – creates a new record based on [record] with changes from a list of pairs
* get-properties record – returns all keys in this record as a list of strings
* assert list-of-assertions – tests every assertion in the list of pairs, return false if any assertion fails
* sh cmd – executes the given command in the current working directory and returns the output as a list of strings (
  lines)
* $ cmd – executes the given command in the current working directory and returns the output as a list of strings (
  lines)
* cd dir – changes the current working directory to a new directory
* CWD – resets the current working directory
* env – holds all environment variables and their values as a record
* system – holds all java system properties their values as a record (names with a '.' are replaced by '-')
* f ** g – combines two functions to a new one that applies both functions after another
* f <| v – pipes the [value] on the right into the [function] on the left
* f ◁ v – pipes the [value] on the right into the [function] on the left
* when* v list – checks each [predicate] called with [value] to evaluate to first expression with a true predicate
* make-pair-list list – converts a list of 2-item-lists to a list of pairs
* id a – returns the given parameter as is
* or expression-list – returns the first value that is not falsey
* print v – prints the parameters and appends a newline
