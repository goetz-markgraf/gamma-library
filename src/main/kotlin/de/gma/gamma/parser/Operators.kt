package de.gma.gamma.parser


const val OP_LEVEL_DEFAULT = 3
const val OP_LEVEL_PAIR = 8

val operatorLevels = listOf(
    /* Level 0 */    mutableListOf("^", "..", "::", "@"),
    /* Level 1 */    mutableListOf("*", "/", "×", "÷"),
    /* Level 2 */    mutableListOf("+", "-"),
    /* Level 3 */    mutableListOf(),
    /* Level 4 */    mutableListOf(">", ">=", "≥", "<", "<=", "≤", "=", "≠", "!="),
    /* Level 5 */    mutableListOf("&", "∧"),
    /* Level 6 */    mutableListOf("|", "∨"),
    /* Level 7 */    mutableListOf("|>", "▷", "<|", "◁"),
    /* Level 8 */    mutableListOf("->", "→")
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
