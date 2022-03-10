package de.gma.gamma.lexer

const val MINUS = '-'
const val DOT = '.'
const val ESC = '\\'
const val DQUOTE = '\"'
const val RETURN = '\n'

fun isParens(char: Char) =
    "()[]{}".contains(char)

fun isNumberChar(char: Char) = Character.isDigit(char)

fun isFirstNumberChar(char: Char, peekChar: Char) =
    isNumberChar(char)
            || (char == MINUS && (peekChar == DOT || isNumberChar(peekChar)))
            || (char == DOT && isNumberChar(peekChar))
