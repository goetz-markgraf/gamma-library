package de.gma.gamma.parser


const val OP_LEVEL_POWER = 0
const val OP_LEVEL_PRODUCT = 1
const val OP_LEVEL_SUM = 2
const val OP_LEVEL_DEFAULT = 3
const val OP_LEVEL_COMPARISON = 4
const val OP_LEVEL_OR = 5
const val OP_LEVEL_AND = 6
const val OP_LEVEL_ARROW = 7

val operatorLevels = listOf(
    mutableListOf("^", "..", "::", "@"),
    mutableListOf("*", "/", "×", "÷"),
    mutableListOf("+", "-"),
    mutableListOf(),
    mutableListOf(">", ">=", "≥", "<", "<=", "≤", "=", "≠", "!="),
    mutableListOf("&", "⋀"),
    mutableListOf("|", "⋁"),
    mutableListOf("->", "|>")
)

const val MAX_OPERATOR_LEVEL = OP_LEVEL_ARROW

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
