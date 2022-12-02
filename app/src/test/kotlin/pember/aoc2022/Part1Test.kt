package pember.aoc2022


import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Part1Test {

    @Test
    fun `Day 1 - calorie counting -part 1`() {
        val max = CalorieCounting("day1-test.txt").findMaxCal()
        assertThat(max).isEqualTo(24000)
        val max2 = CalorieCounting("day1.txt").findMaxCal()
        assertThat(max2).isEqualTo(69528)
    }

    @Test
    fun `Day 1 - calorie counting -part 2`() {
        val max = CalorieCounting("day1-test.txt").findTop3Cals()
        assertThat(max).isEqualTo(45000)
        val max2 = CalorieCounting("day1.txt").findTop3Cals()
        assertThat(max2).isEqualTo(206152)
    }

    @Test
    fun `Day 2- RPS - part 1`() {

        assertThat(RockPaperScissors("day2-test.txt").getNaiveTotalScore()).isEqualTo(15)

        assertThat(RockPaperScissors("day2.txt").getNaiveTotalScore()).isEqualTo(13268)
    }
    @Test
    fun `Day 2 - RPS - part 2`() {
        assertThat(RockPaperScissors("day2-test.txt").getUltraSecretStrategyScore()).isEqualTo(12)
        assertThat(RockPaperScissors("day2.txt").getUltraSecretStrategyScore()).isEqualTo(15508)
    }
}