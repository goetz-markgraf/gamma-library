package de.gma.gamma.parser

const val CH_MINUS = '-'
const val CH_PLUS = '+'
const val CH_MUL = '*'
const val CH_DOT = '.'
const val CH_ESC = '\\'
const val CH_QUOTE = '\"'
const val CH_NEWLINE = '\n'
const val CH_APOSTR = '\''
const val CH_HASH = '#'
const val CH_LPARENS = '('
const val CH_RPARENS = ')'
const val CH_COLON = ':'
const val CH_UNDERSCORE = '_'
const val CH_BANG = '!'
const val CH_QUESTION_MARK = '?'
const val CH_DOLLAR = '$'

const val nullChar = Char.MIN_VALUE

fun isStartOfProperty(char: Char, peekChar: Char) =
    char == CH_COLON && isStartOfIdentifier(peekChar)

fun isStartOfDocumentation(char: Char) =
    char == CH_APOSTR

fun isStartOfLineComment(char: Char) =
    char == CH_HASH

fun isEof(char: Char) =
    char == nullChar

fun isParens(char: Char) =
    "()[]{}".contains(char)

fun isVoid(char: Char, peekChar: Char) =
    char == CH_LPARENS && peekChar == CH_RPARENS

fun isWhitespace(char: Char) =
    char.isWhitespace()


fun isNumberChar(char: Char) = char.isDigit()


fun isStartOfNumber(char: Char, peekChar: Char) =
    isNumberChar(char)
            || (char == CH_MINUS && (peekChar == CH_DOT || isNumberChar(peekChar)))
            || (char == CH_DOT && isNumberChar(peekChar))


fun isStartOfIdentifier(char: Char) =
    char.isLetter() || char == CH_UNDERSCORE || char == CH_DOLLAR

fun isIdentifierChar(char: Char) =
    char.isLetter() || char.isDigit() || isValidSpecialIdentifierChar(char) || isEndOfIdentifier(char)

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

/**
 * Returns true if [char] may appear inside an operator token.
 *
 * Strategy: accept any Unicode symbol / punctuation category, then
 * explicitly exclude characters that have dedicated lexer roles.
 *
 * Excluded:
 *   - nullChar          EOF sentinel
 *   - whitespace        token separator (including tab used for indentation)
 *   - letters / digits  identifiers and numbers
 *   - ( ) [ ] { }      bracket tokens
 *   - _                 identifier char
 *   - $                 string interpolation  $(
 *   - #                 line comment start
 *   - '  (U+0027)       ASCII apostrophe - documentation block delimiter
 *   - "  (U+0022)       ASCII quote - string literal delimiter
 *   - ,  ;              expression-ending characters
 *
 * Note: surrogate halves (type SURROGATE=19) are accepted so that multi-char
 * Unicode codepoints (e.g. emoji) work when the lexer iterates char-by-char.
 */
private val OPERATOR_EXCLUDED = setOf(nullChar, '_', '$', '#', CH_APOSTR, CH_QUOTE, ',', ';')

fun isOperatorChar(char: Char): Boolean {
    if (char == nullChar) return false
    if (isWhitespace(char)) return false
    if (char.isLetter() || char.isDigit()) return false
    if ("()[]{}".contains(char)) return false
    if (char in OPERATOR_EXCLUDED) return false

    val type = Character.getType(char)
    return type == Character.MATH_SYMBOL.toInt()               // Sm: +, x, /, sum, <=, pipe ...
        || type == Character.OTHER_SYMBOL.toInt()              // So: copyright, pointing hand, heart ...
        || type == Character.MODIFIER_SYMBOL.toInt()           // Sk: ^ and modifier glyphs
        || type == Character.DASH_PUNCTUATION.toInt()          // Pd: -, en-dash, em-dash
        || type == Character.OTHER_PUNCTUATION.toInt()         // Po: !, %, &, *, :, ?, @, \ ...
        || type == Character.CONNECTOR_PUNCTUATION.toInt()     // Pc: _ (already excluded above)
        || type == Character.INITIAL_QUOTE_PUNCTUATION.toInt() // Pi: <<
        || type == Character.FINAL_QUOTE_PUNCTUATION.toInt()   // Pf: >>
        || type == Character.SURROGATE.toInt()                 // surrogate halves for emoji etc.
}

fun isStartOfFunctionOperator(char: Char, peekChar: Char, peekPeekChar: Char) =
    char == CH_LPARENS && isOperatorChar(peekChar) && isOperatorChar(peekPeekChar) ||
            char == CH_LPARENS && isOperatorChar(peekChar) && peekPeekChar == CH_RPARENS

fun isExpressionEndingChar(char: Char) =
    ",;".contains(char)

fun isTernaryCharacter(char: Char) =
    char == CH_QUESTION_MARK
