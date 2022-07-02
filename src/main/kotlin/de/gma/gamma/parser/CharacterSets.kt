package de.gma.gamma.parser

import java.lang.Character.MIN_VALUE as nullChar


const val CH_MINUS = '-'
const val CH_PLUS = '+'
const val CH_MUL = '*'
const val CH_DOT = '.'
const val CH_ESC = '\\'
const val CH_QUOTE = '\"'
const val CH_NEWLINE = '\n'
const val CH_UNIT1 = '('
const val CH_UNIT2 = ')'
const val CH_APOSTR = '\''
const val CH_HASH = '#'
const val CH_LPARENS = '('
const val CH_RPARENS = ')'
const val CH_COLON = ':'
const val CH_UNDERSCORE = '_'
const val CH_BANG = '!'
const val CH_QUESTION_MARK = '?'


fun isStartOfProperty(char: Char, peekChar: Char, peekPeekChar: Char) =
    char == CH_COLON && isStartOfIdentifier(peekChar, peekPeekChar)

fun isStartOfDocumentation(char: Char) =
    char == CH_APOSTR

fun isStartOfRemark(char: Char) =
    char == CH_HASH

fun isEof(char: Char) =
    char == nullChar

fun isParens(char: Char) =
    "()[]{}".contains(char)

fun isUnit(char: Char, peekChar: Char) =
    char == CH_UNIT1 && peekChar == CH_UNIT2

fun isWhitespace(char: Char) =
    Character.isWhitespace(char)


fun isNumberChar(char: Char) = Character.isDigit(char)


fun isStartOfNumber(char: Char, peekChar: Char) =
    isNumberChar(char)
            || (char == CH_MINUS && (peekChar == CH_DOT || isNumberChar(peekChar)))
            || (char == CH_DOT && isNumberChar(peekChar))


fun isStartOfIdentifier(char: Char, peekChar: Char) =
    Character.isLetter(char) || char == CH_UNDERSCORE && Character.isLetter(peekChar)

fun isIdentifierChar(char: Char) =
    Character.isLetter(char) || Character.isDigit(char) || isValidSpecialIdentifierChar(char) || isEndOfIdentifier(char)

fun isValidSpecialIdentifierChar(char: Char) =
    char == CH_MUL
            || char == CH_MINUS
            || char == CH_PLUS
            || char == CH_UNDERSCORE

fun isEndOfIdentifier(char: Char) =
    char == CH_BANG || char == CH_QUESTION_MARK

fun isStartOfString(char: Char) =
    char == CH_QUOTE

fun isColon(char: Char) =
    char == CH_COLON

fun isOperatorChar(char: Char) =
    ".<>-+*^/\\:%$|=!&@×⋀⋁÷≠≤≥∑∫◊≈∆►◀︎☞☛".contains(char)

fun isStartOfFunctionOperator(char: Char, peekChar: Char, peekPeekChar: Char) =
    char == CH_LPARENS && isOperatorChar(peekChar) && isOperatorChar(peekPeekChar) ||
            char == CH_LPARENS && isOperatorChar(peekChar) && peekPeekChar == CH_RPARENS

fun isExpressionEndingChar(char: Char) =
    ",;".contains(char)

fun isTeneryCharacter(char: Char) =
    char == CH_QUESTION_MARK
