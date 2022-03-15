package de.gma.gamma.parser

import java.lang.Character.MIN_VALUE as nullChar


const val MINUS = '-'
const val DOT = '.'
const val ESC = '\\'
const val QUOTE = '\"'
const val NEWLINE = '\n'
const val TICK = '´'
const val BACKTICK = '`'
const val UNIT1 = '('
const val UNIT2 = ')'
const val COMMENT = '#'


fun isStartOfComment(char: Char) =
    char == COMMENT

fun isEof(char: Char) =
    char == nullChar

fun isParens(char: Char) =
    "()[]{}".contains(char)

fun isUnit(char: Char, peekChar: Char) =
    char == UNIT1 && peekChar == UNIT2

fun isWhitespace(char: Char) =
    Character.isWhitespace(char)


fun isNumberChar(char: Char) = Character.isDigit(char)


fun isStartOfNumber(char: Char, peekChar: Char) =
    isNumberChar(char)
            || (char == MINUS && (peekChar == DOT || isNumberChar(peekChar)))
            || (char == DOT && isNumberChar(peekChar))


fun isStartOfIdentifier(char: Char) =
    Character.isLetter(char)

fun isIdentifierChar(char: Char) =
    Character.isLetter(char) || Character.isDigit(char) || isValidSpecialIdentifierChar(char)

fun isValidSpecialIdentifierNonEndingChar(char: Char) =
    ".-_+".contains(char)

fun isValidSpecialIdentifierChar(char: Char) =
    "*!?".contains(char)

fun isStartOfString(char: Char) =
    char == QUOTE


fun isOperatorChar(char: Char) =
    "<>-+^/\\:%$|=!&".contains(char)


fun isTickedIdentifierChar(char: Char, peekChar: Char) =
    (char == TICK || char == BACKTICK) && isStartOfIdentifier(peekChar)

fun isTickedOperatorChar(char: Char, peekChar: Char) =
    (char == TICK || char == BACKTICK) && isOperatorChar(peekChar)

fun isExpressionEndingChar(char: Char) =
    ",;".contains(char)
