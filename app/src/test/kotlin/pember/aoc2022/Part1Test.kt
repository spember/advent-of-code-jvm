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

    @Test
    fun `Day 6 - Tuning 1`() {
        assertThat(TuningTrouble().detectMarkerFromFile("day6-test.txt")).isEqualTo(7)
        assertThat(TuningTrouble().detectMarker("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(5)
        assertThat(TuningTrouble().detectMarker("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(6)
        assertThat(TuningTrouble().detectMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(10)
        assertThat(TuningTrouble().detectMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(11)
        assertThat(TuningTrouble().detectMarkerFromFile("day6.txt")).isEqualTo(1531)
    }

    @Test
    fun `Day 6 - Tuning 2`() {
        assertThat(TuningTrouble().detectMessageFromFile("day6-test.txt")).isEqualTo(19)
        assertThat(TuningTrouble().detectMessage("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(23)
        assertThat(TuningTrouble().detectMessage("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(23)
        assertThat(TuningTrouble().detectMessage("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(29)
        assertThat(TuningTrouble().detectMessage("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(26)
        assertThat(TuningTrouble().detectMessageFromFile("day6.txt")).isEqualTo(2518)
    }

    @Test
    fun `Day 7 - scanning 1`() {
        assertThat(NoSpaceLeft("day7-test.txt").findSpaceToClear()).isEqualTo(95437L)
        assertThat(NoSpaceLeft("day7.txt").findSpaceToClear()).isEqualTo(1447046L)
    }

    @Test
    fun `Day 7 - scanning 2`() {
        assertThat(NoSpaceLeft("day7-test.txt").findSmallestDir()).isEqualTo(24933642L)
        assertThat(NoSpaceLeft("day7.txt").findSmallestDir()).isEqualTo(578710L)
    }

    @Test
    fun `Day 8 - find visible1`() {
        assertThat(TreetopTreeHouse("day8-test.txt").countVisible()).isEqualTo(21)
        assertThat(TreetopTreeHouse("day8.txt").countVisible()).isEqualTo(1779)
    }

    @Test
    fun `Day 8 - find scenic`() {
        assertThat(TreetopTreeHouse("day8-test.txt").findMaxScenic()).isEqualTo(8)
        assertThat(TreetopTreeHouse("day8.txt").findMaxScenic()).isEqualTo(172224)
    }
}