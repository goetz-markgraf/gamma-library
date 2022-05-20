# GAMMA

a functional programming language interpreter written in kotlin

## Introduction

`gamma-library` is a parser and interpreter for the `gamma` programming language. It is a functional programming language that borrows heavyly from two sources:

- lisp – for the idea of the evaluation loop and the basic inner workings
- ML – for the idea for the syntax of the language

The idea is to create a language that combines the simplicity of the inner workings of lips with the easy to write and read syntax of ML-based languages.

## Usage

You can link this library to your project and use it to include a scripting language in your software.

To use `gamma-library` simply call these functions with your `gamma`-Sourcecode in the variable `code`

```
        val scope = ModuleScope()

        val parser = Parser(code)
        var expr = parser.nextExpression(-1)

        while (expr != null) {
            val result = expr.evaluate(scope)
            if (result !is Expression && result !is AbstractFunction) // these lines only if you want to display the result
                println("-> ${result.prettyPrint()}")                 // these lines only if you want to display the result

            expr = parser.nextExpression(-1)
        }

```

The `parser` takes the code as a parameter. If your code is read from a file, supply the filename as a second parameter for better error messages in case of exceptions: `Parser(code, filename)`.

The function `nextExpression()` returns the next parsed `expression` in for of a valid `gamma`datatype, or`null`if the`Parser` has reached the end of the sourcecode.

The returned `expression` can now be evaluated within a `scope`.

## add your own functions

If you want to enhance the functionality of `gamma` with your own functions, you have to add them to the scope before executing the first `expression`

```
    scope.bind(name, functionImplementation)
```

### write your own function implementation

TBD[](https://)
