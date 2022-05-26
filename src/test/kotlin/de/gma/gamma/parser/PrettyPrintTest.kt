package de.gma.gamma.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PrettyPrintTest : BaseParserTest() {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "true",
            "false",
            "10",
            "1.5",
            "-1",
            "-1.0",
            "{ }",
            "()",
            ":prop",
            "\"a\"",
            "\"\""
        ]
    )
    fun `keywords and literals print their source code`(source: String) {
        val exp = getExpression(source)

        assertThat(exp!!.prettyPrint()).isEqualTo(source)
    }
}
