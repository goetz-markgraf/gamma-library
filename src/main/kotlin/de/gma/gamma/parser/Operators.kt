package de.gma.gamma.parser


const val OP_LEVEL_DEFAULT = 3
const val OP_LEVEL_PAIR = 8

val operatorLevels = listOf(
    /* Level 0 */    listOf("^", "..", "::", "@"),
    /* Level 1 */    listOf("*", "/", "×", "÷"),
    /* Level 2 */    listOf("+", "-"),
    /* Level 3 */    listOf(), // nothing in here, because it is never used builtin
    /* Level 4 */    listOf(">", ">=", "≥", "<", "<=", "≤", "=", "≠", "!="),
    /* Level 5 */    listOf("&", "∧"),
    /* Level 6 */    listOf("|", "∨"),
    /* Level 7 */    listOf("|>", "▷", "<|", "◁"),
    /* Level 8 */    listOf("->", "→")
)

const val MAX_OPERATOR_LEVEL = OP_LEVEL_PAIR

fun isOperatorInLevel(op: String, level: Int): Boolean {
    if (level < 0 || level > MAX_OPERATOR_LEVEL)
        return false

    val operatorSet = operatorLevels[level]
    if (operatorSet.isNotEmpty())
        return operatorSet.contains(op)

    // return true if operator is not in any of the sets
    return operatorLevels.find { it.contains(op) } == null
}

fun operatorLevel(s: String): Int {
    var level = -1
    operatorLevels.forEachIndexed { index, strings -> if (strings.contains(s)) level = index }

    return if (level >= 0)
        level
    else
        OP_LEVEL_DEFAULT
}
