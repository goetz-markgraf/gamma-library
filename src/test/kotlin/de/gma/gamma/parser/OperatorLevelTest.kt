package de.gma.gamma.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OperatorLevelTest : BaseParserTest() {

    @Test
    fun `cannot retrieve negative operatorlevel`() {
        val result = isOperatorInLevel("x", -1)

        assertThat(result).isFalse
    }

    @Test
    fun `cannot retrieve operatorlevel too hight`() {
        val result = isOperatorInLevel("x", MAX_OPERATOR_LEVEL + 1)

        assertThat(result).isFalse
    }
}
