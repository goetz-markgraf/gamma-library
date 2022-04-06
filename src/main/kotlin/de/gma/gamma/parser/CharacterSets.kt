package de.gma.gamma.parser

import java.lang.Character.MIN_VALUE as nullChar


const val MINUS = '-'
const val PLUS = '+'
const val MUL = '*'
const val DOT = '.'
const val ESC = '\\'
const val QUOTE = '\"'
const val NEWLINE = '\n'
const val UNIT1 = '('
const val UNIT2 = ')'
const val COMMENT = '/'
const val LPARENS = '('
const val RPARENS = ')'
const val HASH = '#'
const val COLON = ':'
const val UNDERSCORE = '_'
const val BANG = '!'
const val QUESTION_MARK = '?'


fun isStartOfProperty(char: Char) =
    char == HASH

fun isStartOfComment(char: Char, peekChar: Char) =
    char == COMMENT && peekChar == COMMENT

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


fun isStartOfIdentifier(char: Char, peekChar: Char) =
    Character.isLetter(char) || char == UNDERSCORE && Character.isLetter(peekChar)

fun isIdentifierChar(char: Char) =
    Character.isLetter(char) || Character.isDigit(char) || isValidSpecialIdentifierChar(char)

fun isValidIdentifierSeparatorChar(char: Char) =
    char == MINUS || char == PLUS || char == DOT || char == UNDERSCORE

fun isValidSpecialIdentifierChar(char: Char) =
    char == MUL || char == BANG || char == QUESTION_MARK

fun isStartOfString(char: Char) =
    char == QUOTE

fun isSpread(char: Char, peekChar: Char, peekPeekChar: Char) =
    char == DOT && peekChar == DOT && peekPeekChar == DOT

fun isColon(char: Char) =
    char == COLON

fun isOperatorChar(char: Char) =
    "<>-+*^/\\:%$|=!&".contains(char)

fun isStartOfFunctionOperator(char: Char, peekChar: Char) =
    char == LPARENS && isOperatorChar(peekChar)

fun isExpressionEndingChar(char: Char) =
    ",;".contains(char)

fun isElvisCharacter(char: Char) =
    char == QUESTION_MARK
