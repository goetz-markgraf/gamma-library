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
  
    # is is an "if" expression that evaluate the second or third expression, depending
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

## usage of function names in infix position

If you have a function that takes two parameters, you can write this function in infix position. You
simply have to place a colon `:` after the name of the function.

```
    # add function taking two number values
    let add a b = a + b
    
    # these two expressions produce identical results
    add 10 20
    10 add: 20
```

Keep in mind that the order of parameters is not changed:

```
    let list = {1, 2, 3}
    
    let map-function it = it * 2
    
    # normal way of writing
    map map-function list
    
    # function name in infix position
    map-function map: list
    
    # and not:
    # list map: map-function
```

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
  # create a record with the 'record'-function
  let a =
    record {
      :name -> "Mueller"
      :first-name -> "Willi"
      :birthdate -> "01.01.1980"
    }
```

_Alternatively you can also use the `r*`-function that is a shortcut for `record`._

Accessing the record is possible via the property names that work like getter functions:

```
  # getting the name value of the record 'a'
  print (:name a)
```

Like all other data structures in _gamma_ a `record`-Type is immutable. You can create a modified
copy with the `copy-with`-function:

```
  # create a sibling of the person in a
  let b =
    a |> copy-with { :first-name -> "Sarah" } 
```

# Built-in functions

These are the functions currenty built in into _gamma_:

* is-boolean? obj – checks if &lt;obj&gt; is of type boolean
* is-list? obj – checks if &lt;obj&gt; is of type list
* is-float? obj – checks if &lt;obj&gt; is of type float
* is-integer? obj – checks if &lt;obj&gt; is of type integer
* is-string? obj – checks if &lt;obj&gt; is of type string
* is-function? obj – checks if &lt;obj&gt; is of type function
* is-record? obj – checks if &lt;obj&gt; is of type record
* is-property? obj – checks if &lt;obj&gt; is of type property
* is-void? obj – checks if &lt;obj&gt; is of type void
* is-module? obj – checks if &lt;obj&gt; is of type module
* can-be-used-as-boolean? obj – checks if &lt;obj&gt; can be used as a type boolean
* can-be-used-as-list? obj – checks if &lt;obj&gt; can be used as a type list
* can-be-used-as-float? obj – checks if &lt;obj&gt; can be used as a type float
* can-be-used-as-integer? obj – checks if &lt;obj&gt; can be used as a type integer
* can-be-used-as-string? obj – checks if &lt;obj&gt; can be used as a type string
* can-be-used-as-function? obj – checks if &lt;obj&gt; can be used as a type function
* can-be-used-as-record? obj – checks if &lt;obj&gt; can be used as a type record
* can-be-used-as-property? obj – checks if &lt;obj&gt; can be used as a type property
* can-be-used-as-emptyvalue? obj – checks if &lt;obj&gt; can be used as a type emptyvalue
* can-be-used-as-module? obj – checks if &lt;obj&gt; can be used as a type module
* v |&gt; f – calls &lt;function&gt; with &lt;value&gt; as parameter
* v ▷ f – calls &lt;function&gt; with &lt;value&gt; as parameter
* v ⊳ f – calls &lt;function&gt; with &lt;value&gt; as parameter
* debug - evaluates the DebugValue for debug purposes
* break - evaluates the DebugValue for debug purposes
* print value – prints a value to standard out
* print* value – prints a value to standard out without a newline at the end
* read-lines filename – reads the content of &lt;filename&gt; as a list of strings
* split string – splits a string separated by blank into a list of strings
* split-by separator string – splits a string separated by &lt;separator&gt; into a list of strings
* join list – joins all elements of &lt;list&gt; together as one string, seperated by a blank
* join-by string list – joins all elements of &lt;list&gt; together as one string, seperated by &lt;string&gt;
* a + b – adds two number values
* a - b – subtract &lt;b&gt; from &lt;a&gt;
* a * b – multiplies two number values
* a × b – multiplies two number values
* a / b – divides &lt;a&gt; by &lt;b&gt;
* a ÷ b – divides &lt;a&gt; by &lt;b&gt;
* a ^ b – raise &lt;a&gt; to the &lt;b&gt;th power
* neg a – return the negative of a number
* min list – returns the smallest number
* max list – returns the largest number
* abs value – returns the absolute of the number
* first list – returns the first element or () if empty list
* second list – returns the second element or () if there is none
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
* appendAll item list – appends all items of &lt;new-list&gt; the end of &lt;list&gt;
* item :: list – prepends a list with an as the new first element
* list-1 @ list-2 – concats &lt;list-1&gt; and &lt;list-2&gt;
* list-generator size function – creates a list of size &lt;size&gt; that uses &lt;function&gt; to generate the values
* repeat size – create a list from 0 to &lt;repeate&gt; - 1
* from .. to – create a list from &lt;from&gt; to &lt;to&gt; inclusive
* map function list – creates a new list with every item of &lt;list&gt; run through &lt;function&gt;
* map* function list – creates a new list with every item of &lt;list&gt; run through &lt;function&gt; with
  &lt;index&gt; value
* for-each function list – calls &lt;function&gt; for every item of &lt;list&gt;
* fold initial function list – folds the &lt;list&gt; with &lt;function&gt; and &lt;initial&gt; acc
* reduce function list – reduces the &lt;list&gt; with &lt;function&gt;
* filter predicate list – filters the &lt;list&gt; such that it contains only elements the match the &lt;predicate&gt;
* zip list-1 list-2 – Creates a new list consisting of pairs of the curresponding elements from &lt;list-1&gt; and
  &lt;list-2&gt;
* contains? predicate list – tests if &lt;item&gt; is in &lt;list&gt;. This also includes strings.
* does-not-contain? predicate list – tests if &lt;item&gt; is not in &lt;list&gt;. This also includes strings.
* is-empty? list – tests if &lt;list or string&gt; is empty, i. e. does not contain elements or characters
* is-not-empty? list – tests if &lt;list or string&gt; contains at least one element or character
* when cases – checks each predicate to evaluate to first expression with a true predicate
* a = b – checks if &lt;a&gt; is equal to &lt;b&gt;
* a != b – checks if &lt;a&gt; is not equal to &lt;b&gt;
* a ≠ b – checks if &lt;a&gt; is not equal to &lt;b&gt;
* a &gt; b – checks if the first value is greater than the second
* a &gt;= b – checks if the first value is greater than or equal to the second
* a ≥ b – checks if the first value is greater than or equal to the second
* a &lt; b – checks if the first value is less than the second
* a &lt;= b – checks if the first value is less than or equal to the second
* a ≤ b – checks if the first value is less than or equal to the second
* a & b – returns true if &lt;a&gt; and &lt;b&gt; are both true
* a ∧ b – returns true if &lt;a&gt; and &lt;b&gt; are both true
* a | b – returns true if either &lt;a&gt; or &lt;b&gt; or both are true
* a ∨ b – returns true if either &lt;a&gt; or &lt;b&gt; or both are true
* not a – returns true if &lt;a&gt; is false and false if &lt;a&gt; is true
* pipeline id list-of-expressions – executes each &lt;expression&gt;, always using &lt;id&gt; for the result of the
  previous one
* else - equal to true, can be used in when expressions
* at pos list – returns the &lt;pos&gt;th element of this list
* at* pos list – returns the &lt;pos&gt;th element of this list or () if not existing
* record property-list – creates a record from a list of pairs
* r* property-list – creates a record from a list of pairs
* copy-with property-list record – creates a new record based on &lt;record&gt; with changes from a list of pairs
* get-properties record – returns all keys in this record as a list of strings
* assert list-of-assertions – tests every assertion in the list of pairs, return false if any assertion fails
