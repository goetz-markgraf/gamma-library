# Learn to program in _Gamma_

_(Please keep in mind that __Gamma__ is still work in progress, not free from errors and probably changin
a lot in the future)_

## Introduction

Gamma is an expression based functional language. That meens that everything in Gamma is an executable
expression and has a value. There are only very few keywords or language constructs, almost everything is
or can be done with writing and calling functions.

## Basic Syntax

_Gamma_ uses indentation to structure blocks if they are not already structures by parenthesis or brackets.

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

_Gamma_ knows the following value literals

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
    # the tree following expressions produce equal results 
    {1, 2, 3}
    {1; 2; 3} 
    {
        1
        2
        3
    }
```

A list can hold any number of values of any type.

## calling funtions

There are two kinds of function call in _Gamma_.

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

## operators

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

### usage of operators as function names

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
operator. That is why in _Gamma_ the parameter indicating the value to work on comes last.

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

As I have said before, functions are also values that can be assigned. Function can easily be
created "on the fly" (aka lambda function) using `[` and `]` and a `->` to seperate parameters from the code. Actually, the
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

Apart from lists _Gamma_ can create `record`-Types that match a value to a property name.

```
  # create a record with the 'record'-function
  let a =
    record {
      :name -> "Mueller"
      :first-name -> "Willi"
      :birthdate -> "01.01.1980"
    }
```

Accessing the record is possible via the property names that work like getter functions:

```
  # getting the name value of the record 'a'
  print (:name a)
```

Like all other data structures in _Gamma_ a `record`-Type is immutable. You can create a modified
copy with the `copy-with`-function:

```
  # create a sibling of the person in a
  let b =
    a |> copy-with { :first-name -> "Sarah" } 
```



# Built-in functions

These are the functions currenty built in into _Gamma_:

* 'print <value> - prints a value to standard out'
* 'print* <value> - prints a value to standard out without a newline at the end'
* 'read-lines <filename> - reads the content of <filename> as a list of strings'
* 'split <string> - splits a string separated by blank into a list of strings'
* 'join <string> <list> - joins all elements of <list> together as one string, seperated by <string>'
* '<a> + <b> - adds two number values'
* '<a> - <b> - subtract <b> from <a>'
* '<a> * <b> - multiplies two number values'
* '<a> / <b> - divides <a> by <b>'
* '<a> ^ <b> - raise <a> to the <b>th power'
* 'neg <a> - return the negative of a number'
* '<value> |> <function> - calls <function> with <value> as parameter'
* 'first <list> – returns the first element or () if empty list'
* 'second <list> – returns the second element or () if there is none'
* 'first <list> – returns the first element or () if empty list'
* 'tail <list> – returns all but the fist element or empty list if empty list'
* 'last <list> – returns the last element or () if empty list'
* 'drop-last <list> – returns all but the last element or empty list if empty list'
* 'slice <from> <length> <list> – returns a sublist'
* 'size <list> – returns the number of elements in this list'
* 'append <item> <list> – appends an item to the end of the list'
* 'appendAll <new-list> <list> – appends all items of <new-list> the end of <list>'
* '<item> :: <list> – prepends a list with an as the new first element'
* 'list-generator <size> <function> – creates a list of size <size> that uses <function> to generate the values'
* 'repeat <num> - create a list from 0 to <repeate> - 1'
* '<start> .. <end> - create a list from start> to <end> inclusive'
* 'map <function> <list> – creates a new list with every item of <list> run through <function>'
* 'for-each <function> <list> – calls <function> for every item of <list>'
* 'fold <inital> <function> <list> – folds the <list> with <function> and <initial> acc'
* 'reduce <function> <list> – reduces the <list> with <function>'
* 'reduce <predicate> <list> – filters the <list> such that it contains only elements the match the <predicate>'
* 'contains? <item> <list> – tests if <item> is in <list>. This also includes strings.'
* 'is-list? <value> – tests if <value> is a list. This also includes strings'
* 'is-string? <value> – tests if <value> is a string'
* 'is-pair? <value> – tests if <value> is a pair, i. e. a list with two elements'
* 'is-empty? <list or string> – tests if <list or string> is empty, i. e. does not contain elements or characters'
* 'is-not-empty? <list or string> – tests if <list or string> contains at least one element or character'
* 'when <list of predicate/expression lists> - checks each predicate to evaluate to first expression with a true
  predicate'
* '<a> = <b> - checks if <a> is equal to <b>'
* '<a> != <b> - checks if <a> is not equal to <b>'
* '<a> > <b> - checks if the first value is greater than the second'
* '<a> >= <b> - checks if the first value is greater than or equal to the second'
* '<a> < <b> - checks if the first value is less than the second'
* '<a> <= <b> - checks if the first value is less than or equal to the second'
* '<a> & <b> - returns true if <a> and <b> are both true'
* '<a> | <b> - returns true if either <a> or <b> or both are true'
* 'not <a> - returns true if <a> is false and false if <a> is true'
* 'else - the same as true'
* 'at <pos> <list> – returns the <pos>th element of this list'
* 'record <list of pair> – creates a record from a list of pairs'
* 'copy <list of pair> <record> – creates a new record based on <record> with changes from a list of pairs'
