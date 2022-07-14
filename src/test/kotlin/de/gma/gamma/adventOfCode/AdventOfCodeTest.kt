package de.gma.gamma.adventOfCode

import de.gma.gamma.datatypes.values.IntegerValue
import de.gma.gamma.evaluation.BaseEvaluationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.BufferedReader
import java.io.InputStreamReader

class AdventOfCodeTest : BaseEvaluationTest() {

    @ParameterizedTest
    @CsvSource(
        value = [
            "day1_1.gma,1583",
            "day1_2.gma,1627",
            "day2_1.gma,1507611",
            "day2_2.gma,1880593125",
            "day3_1.gma,4138664",
            "day3_2.gma,4273224",
            "day4_1.gma,8442",
            "day4_2.gma,4590",
            "day5_1.gma,7269",
            "day5_2.gma,21140",
            "day6_1.gma,365131",
            "day6_2.gma,1650309278600",
            "day7_1.gma,342534",
            "day7_2.gma,94004208"
        ]
    )
    fun `check advent-of-code`(filename: String, expected: Long) {

        val inStream = AdventOfCodeTest::class.java.getResourceAsStream("/advent-of-code/$filename")!!
        val code = BufferedReader(InputStreamReader(inStream)).readText()
        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
    }
}
