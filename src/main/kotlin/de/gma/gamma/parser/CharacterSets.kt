package de.gma.gamma.parser

import java.lang.Character.isDigit
import java.lang.Character.isLetter
import java.lang.Character.MIN_VALUE as nullChar


const val CH_MINUS = '-'
const val CH_PLUS = '+'
const val CH_MUL = '*'
const val CH_DOT = '.'
const val CH_ESC = '\\'
const val CH_QUOTE = '\"'
const val CH_NEWLINE = '\n'
const val CH_VOID1 = '('
const val CH_VOID2 = ')'
const val CH_APOSTR = '\''
const val CH_HASH = '#'
const val CH_LPARENS = '('
const val CH_RPARENS = ')'
const val CH_COLON = ':'
const val CH_UNDERSCORE = '_'
const val CH_BANG = '!'
const val CH_QUESTION_MARK = '?'
const val CH_DOLLAR = '$'


fun isStartOfProperty(char: Char, peekChar: Char) =
    char == CH_COLON && isStartOfIdentifier(peekChar)

fun isStartOfDocumentation(char: Char) =
    char == CH_APOSTR

fun isStartOfRemark(char: Char) =
    char == CH_HASH

fun isEof(char: Char) =
    char == nullChar

fun isParens(char: Char) =
    "()[]{}".contains(char)

fun isVoid(char: Char, peekChar: Char) =
    char == CH_VOID1 && peekChar == CH_VOID2

fun isWhitespace(char: Char) =
    Character.isWhitespace(char)


fun isNumberChar(char: Char) = isDigit(char)


fun isStartOfNumber(char: Char, peekChar: Char) =
    isNumberChar(char)
            || (char == CH_MINUS && (peekChar == CH_DOT || isNumberChar(peekChar)))
            || (char == CH_DOT && isNumberChar(peekChar))


fun isStartOfIdentifier(char: Char) =
    isLetter(char) || char == CH_UNDERSCORE || char == CH_DOLLAR

fun isIdentifierChar(char: Char) =
    isLetter(char) || isDigit(char) || isValidSpecialIdentifierChar(char) || isEndOfIdentifier(char)

fun isValidSpecialIdentifierChar(char: Char) =
    char == CH_MUL
            || char == CH_MINUS
            || char == CH_PLUS
            || char == CH_UNDERSCORE
            || char == CH_DOLLAR

fun isEndOfIdentifier(char: Char) =
    char == CH_BANG || char == CH_QUESTION_MARK

fun isStartOfString(char: Char) =
    char == CH_QUOTE

fun isOperatorChar(char: Char) =
    !(char == nullChar || isWhitespace(char) || isLetter(char) || isDigit(char) || "()[]{}_$".contains(char))
//    ".<>-+*^/:%$|=!&@«»×∧∨÷≠≤≥∑∫◊≈∆▷◁→←\\".contains(char)

fun isStartOfFunctionOperator(char: Char, peekChar: Char, peekPeekChar: Char) =
    char == CH_LPARENS && isOperatorChar(peekChar) && isOperatorChar(peekPeekChar) ||
            char == CH_LPARENS && isOperatorChar(peekChar) && peekPeekChar == CH_RPARENS

fun isExpressionEndingChar(char: Char) =
    ",;".contains(char)

fun isTernaryCharacter(char: Char) =
    char == CH_QUESTION_MARK
