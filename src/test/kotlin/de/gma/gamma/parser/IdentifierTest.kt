package de.gma.gamma.parser

import de.gma.gamma.datatypes.Identifier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IdentifierTest : BaseParserTest() {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "a",
            "a?",
            "a!",
            "a-a",
            "a+a",
            "_a",
            "a1",
            "a123!",
            "a--1++__!",
            "a1!",
            "a1?"
        ]
    )
    fun `parse valid identifiers`(id: String) {
        val expression = getExpression(id) as Identifier

        assertThat(expression.name).isEqualTo(id)

        assertThat(expression.prettyPrint()).isEqualTo(id)
    }

}
