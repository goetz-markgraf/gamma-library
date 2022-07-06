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
  
    # the empty value (aka Unit)
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
created "on the fly" (aka lambda function) using `[` and `]` and a `->` to seperate parameters from the code. Actually,
the
declaration of functions described above is only a shorthand way of assigning a lambda function
to a value

```
  let add a b = a + b
  
  # is the same as
  
  let add = [a b -> a + b]
```

You can use lambdas for example in combination with the map function to work over the items of a list

```
  let list = {1, 2, 3}
  
  # add 10 to every item in the list
  # produces a new list {11, 12, 13}
  
  let result = list |> map [item -> item + 10]
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

* 'print &lt;value&gt; - prints a value to standard out'
* 'print* &lt;value&gt; - prints a value to standard out without a newline at the end'
* 'read-lines &lt;filename&gt; - reads the content of &lt;filename&gt; as a list of strings'
* 'split &lt;string&gt; - splits a string separated by blank into a list of strings'
* 'split-by &lt;separator&gt; &lt;string&gt; - splits a string separated by &lt;separator&gt; into a list of strings'
* 'join &lt;list&gt; - joins all elements of &lt;list&gt; together as one string, seperated by a blank'
* 'join-by &lt;string&gt; &lt;list&gt; - joins all elements of &lt;list&gt; together as one string, seperated by
  &lt;string&gt;'
* '&lt;a&gt; + &lt;b&gt; - adds two number values'
* '&lt;a&gt; - &lt;b&gt; - subtract &lt;b&gt; from &lt;a&gt;'
* '&lt;a&gt; * &lt;b&gt; - multiplies two number values'
* '&lt;a&gt; × &lt;b&gt; - multiplies two number values'
* '&lt;a&gt; / &lt;b&gt; - divides &lt;a&gt; by &lt;b&gt;'
* '&lt;a&gt; ÷ &lt;b&gt; - divides &lt;a&gt; by &lt;b&gt;'
* '&lt;a&gt; ^ &lt;b&gt; - raise &lt;a&gt; to the &lt;b&gt;th power'
* 'neg &lt;a&gt; - return the negative of a number'
* '&lt;value&gt; |&gt; &lt;function&gt; - calls &lt;function&gt; with &lt;value&gt; as parameter'
* '&lt;value&gt; ► &lt;function&gt; - calls &lt;function&gt; with &lt;value&gt; as parameter'
* '&lt;list&gt; =&gt;&gt; &lt;function&gt; - calls map on &lt;list&gt; with &lt;function&gt; as parameter'
* 'first &lt;list&gt; – returns the first element or () if empty list'
* 'second &lt;list&gt; – returns the second element or () if there is none'
* 'head &lt;list&gt; – returns the first element or () if empty list'
* 'tail &lt;list&gt; – returns all but the fist element or empty list if empty list'
* 'last &lt;list&gt; – returns the last element or () if empty list'
* 'drop-last &lt;list&gt; – returns all but the last element or empty list if empty list'
* 'slice &lt;from&gt; &lt;length&gt; &lt;list&gt; – returns a sublist'
* 'size &lt;list&gt; – returns the number of elements in this list'
* 'append &lt;item&gt; &lt;list&gt; – appends an item to the end of the list'
* 'appendAll &lt;new-list&gt; &lt;list&gt; – appends all items of &lt;new-list&gt; the end of &lt;list&gt;'
* '&lt;item&gt; :: &lt;list&gt; – prepends a list with an as the new first element'
* 'list-generator &lt;size&gt; &lt;function&gt; – creates a list of size &lt;size&gt; that uses &lt;function&gt; to
  generate the values'
* 'repeat &lt;num&gt; - create a list from 0 to &lt;repeate&gt; - 1'
* '&lt;start&gt; .. &lt;end&gt; - create a list from start&gt; to &lt;end&gt; inclusive'
* 'map &lt;function&gt; &lt;list&gt; – creates a new list with every item of &lt;list&gt; run through &lt;function&gt;'
* 'for-each &lt;function&gt; &lt;list&gt; – calls &lt;function&gt; for every item of &lt;list&gt;'
* 'fold &lt;inital&gt; &lt;function&gt; &lt;list&gt; – folds the &lt;list&gt; with &lt;function&gt; and &lt;initial&gt;
  acc'
* 'reduce &lt;function&gt; &lt;list&gt; – reduces the &lt;list&gt; with &lt;function&gt;'
* 'filter &lt;predicate&gt; &lt;list&gt; – filters the &lt;list&gt; such that it contains only elements the match the
  &lt;predicate&gt;'
* 'zip &lt;list-1&gt; &lt;list-2&gt; – Creates a new list consisting of pairs of the curresponding elements from
  &lt;list-1&gt; and &lt;list-2&gt;'
* 'contains? &lt;item&gt; &lt;list&gt; – tests if &lt;item&gt; is in &lt;list&gt;. This also includes strings.'
* 'is-list? &lt;value&gt; – tests if &lt;value&gt; is a list. This also includes strings'
* 'is-string? &lt;value&gt; – tests if &lt;value&gt; is a string'
* 'is-pair? &lt;value&gt; – tests if &lt;value&gt; is a pair, i. e. a list with two elements'
* 'is-empty? &lt;list or string&gt; – tests if &lt;list or string&gt; is empty, i. e. does not contain elements or
  characters'
* 'is-not-empty? &lt;list or string&gt; – tests if &lt;list or string&gt; contains at least one element or character'
* 'when &lt;list of predicate/expression lists&gt; - checks each predicate to evaluate to first expression with a true
  predicate'
* '&lt;a&gt; = &lt;b&gt; - checks if &lt;a&gt; is equal to &lt;b&gt;'
* '&lt;a&gt; != &lt;b&gt; - checks if &lt;a&gt; is not equal to &lt;b&gt;'
* '&lt;a&gt; ≠ &lt;b&gt; - checks if &lt;a&gt; is not equal to &lt;b&gt;'
* '&lt;a&gt; &gt; &lt;b&gt; - checks if the first value is greater than the second'
* '&lt;a&gt; &gt;= &lt;b&gt; - checks if the first value is greater than or equal to the second'
* '&lt;a&gt; ≥ &lt;b&gt; - checks if the first value is greater than or equal to the second'
* '&lt;a&gt; &lt; &lt;b&gt; - checks if the first value is less than the second'
* '&lt;a&gt; &lt;= &lt;b&gt; - checks if the first value is less than or equal to the second'
* '&lt;a&gt; ≤ &lt;b&gt; - checks if the first value is less than or equal to the second'
* '&lt;a&gt; & &lt;b&gt; - returns true if &lt;a&gt; and &lt;b&gt; are both true'
* '&lt;a&gt; ⋀ &lt;b&gt; - returns true if &lt;a&gt; and &lt;b&gt; are both true'
* '&lt;a&gt; | &lt;b&gt; - returns true if either &lt;a&gt; or &lt;b&gt; or both are true'
* '&lt;a&gt; ⋁ &lt;b&gt; - returns true if either &lt;a&gt; or &lt;b&gt; or both are true'
* 'not &lt;a&gt; - returns true if &lt;a&gt; is false and false if &lt;a&gt; is true'
* 'else - equal to true, can be used in when expressions'
* 'at &lt;pos&gt; &lt;list&gt; – returns the &lt;pos&gt;th element of this list'
* 'record &lt;list of pair&gt; – creates a record from a list of pairs'
* 'r* &lt;list of pair&gt; – creates a record from a list of pairs'
* 'copy-with &lt;list of pair&gt; &lt;record&gt; – creates a new record based on &lt;record&gt; with changes from a list
  of pairs'
