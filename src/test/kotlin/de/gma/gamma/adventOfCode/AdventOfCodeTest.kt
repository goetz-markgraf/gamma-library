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
            "day1_1.gma,1583,7",
            "day1_2.gma,1627,5",
            "day2_1.gma,1507611,150",
            "day2_2.gma,1880593125,900",
            "day3_1.gma,4138664,198",
            "day3_2.gma,4273224,230",
            "day4_1.gma,8442,4512",
            "day4_2.gma,4590,1924",
            "day5_1.gma,7269,5",
            "day5_2.gma,21140,12",
            "day6_1.gma,365131,5934",
            "day6_2.gma,1650309278600,26984457539",
            "day7_1.gma,342534,37",
            "day7_2.gma,94004208,168",
            "day9_1.gma,530,15",
            "day9_2.gma,1019494,1134",
            "day10_1.gma,339411,26397",
            "day10_2.gma,2289754624,288957",
            "day11_1_mutable.gma,1721,1656",
            "day11_1.gma,1721,1656",
            "day11_2.gma,298,195",
        ]
    )
    fun `check advent-of-code`(filename: String, expected: Long, output: String) {

        val inStream = AdventOfCodeTest::class.java.getResourceAsStream("/advent-of-code/$filename")!!
        val code = BufferedReader(InputStreamReader(inStream)).readText()
        val result = execute(code) as IntegerValue

        assertThat(result.longValue).isEqualTo(expected)
        assertOutput("$output\n")
    }
}
