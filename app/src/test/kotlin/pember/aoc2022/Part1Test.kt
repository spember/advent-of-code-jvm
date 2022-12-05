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

    @Test
    fun `Day 3 - Rucksack 1` () {
        assertThat(RucksackReorganization("day3-test.txt").sumOverlappingPriorities()).isEqualTo(157)
        assertThat(RucksackReorganization("day3.txt").sumOverlappingPriorities()).isEqualTo(7889)
    }
    @Test
    fun `Day 3 - Rucksack 2` () {
        assertThat(RucksackReorganization("day3-test.txt").sumBadgePriority()).isEqualTo(70)
        assertThat(RucksackReorganization("day3.txt").sumBadgePriority()).isEqualTo(2825)
    }

    @Test
    fun `Day 4 - Cleanup 1` () {
        assertThat(CampCleanup("day4-test.txt").countEnclosingPairs()).isEqualTo(2)
        assertThat(CampCleanup("day4.txt").countEnclosingPairs()).isEqualTo(494)
    }

    @Test
    fun `Day 4 - Cleanup 2` () {
        assertThat(CampCleanup("day4-test.txt").countOverlappingPairs()).isEqualTo(4)
        assertThat(CampCleanup("day4.txt").countOverlappingPairs()).isEqualTo(833)
    }
    @Test
    fun `Day 5 - Stacks 1`() {
        assertThat(SupplyStacks("day5-test.txt").findTopCrates()).isEqualTo("CMZ")
        assertThat(SupplyStacks("day5.txt").findTopCrates()).isEqualTo("WSFTMRHPP")
    }

    @Test
    fun `Day 5 - Stacks 2`() {
        assertThat(SupplyStacks("day5-test.txt").findTopCratesWith9001()).isEqualTo("MCD")
        assertThat(SupplyStacks("day5.txt").findTopCratesWith9001()).isEqualTo("GSLCMFBRP")
    }
}