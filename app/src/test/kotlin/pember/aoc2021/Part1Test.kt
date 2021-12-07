package pember.aoc2021

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Part1Test {

    @Test
    fun `day1 - Puzzle1`() {
        assertThat(SonarSweep.initialScan("day1-test.txt")).isEqualTo(7)
        assertThat(SonarSweep.initialScan("day1.txt")).isEqualTo(1215)
    }

    @Test
    fun `day1 - Puzzle2`() {
        assertThat(SonarSweep.deeperScan("day1-test.txt")).isEqualTo(5)
        assertThat(SonarSweep.deeperScan("day1.txt")).isEqualTo(1150)
    }

    @Test
    fun `day2 - Puzzle1`() {
        assertThat(Dive.basicDive("day2-test.txt")).isEqualTo(150)
        assertThat(Dive.basicDive("day2.txt")).isEqualTo(1840243)
    }

    @Test
    fun `day2 - Puzzle2`() {
        assertThat(Dive.aim("day2-test.txt")).isEqualTo(900)
        assertThat(Dive.aim("day2.txt")).isEqualTo(1727785422)
    }

    @Test
    fun `day3 - Puzzle1`() {
        assertThat(BinaryDiagnostic("day3-test.txt").diagnosticReport()).isEqualTo(198)
        assertThat(BinaryDiagnostic("day3.txt").diagnosticReport()).isEqualTo(4139586)
    }

    @Test
    fun `day3 - Puzzle2`() {
        assertThat(BinaryDiagnostic("day3-test.txt").lifeSupportRating()).isEqualTo(230)
        assertThat(BinaryDiagnostic("day3.txt").lifeSupportRating()).isEqualTo(1800151)
    }

    @Test
    fun `day4 - Puzzle1`() {
        assertThat(GiantSquid("day4-test.txt").findWinningBoardScore()).isEqualTo(4512)
        assertThat(GiantSquid("day4.txt").findWinningBoardScore()).isEqualTo(2745)
    }

    @Test
    fun `day4 - Puzzle2`() {
        assertThat(GiantSquid("day4-test.txt").findLosingBoardScore()).isEqualTo(1924)
        assertThat(GiantSquid("day4.txt").findLosingBoardScore()).isEqualTo(6594)
    }

    @Test
    fun `day5 - Puzzle1`() {
        assertThat(HydrothermalVenture("day5-test.txt").findBasicOverLaps()).isEqualTo(5)
        assertThat(HydrothermalVenture("day5.txt").findBasicOverLaps()).isEqualTo(5373)
    }

    @Test
    fun `day5 - Puzzle2`() {
        assertThat(HydrothermalVenture("day5-test.txt").findOverLapsWithDiagonal()).isEqualTo(12)
        assertThat(HydrothermalVenture("day5.txt").findOverLapsWithDiagonal()).isEqualTo(21514)
    }

    @Test
    fun `day6- Puzzle1`() {
        assertThat(LanternfishSpawner("day6-test.txt").simulate(18)).isEqualTo(26)
        assertThat(LanternfishSpawner("day6-test.txt").simulate(80)).isEqualTo(5934)
        assertThat(LanternfishSpawner("day6.txt").simulate(80)).isEqualTo(374994)
    }

    @Test
    fun `day6- Puzzle2`() {
        assertThat(LanternfishSpawner("day6-test.txt").simulate(256)).isEqualTo(26984457539)
        assertThat(LanternfishSpawner("day6.txt").simulate(256)).isEqualTo(1686252324092)
    }

    @Test
    fun `day7 - Puzzle1`() {
        assertThat(TheTreacheryOfWhales("day7-test.txt").alignCrabs()).isEqualTo(37)
        assertThat(TheTreacheryOfWhales("day7.txt").alignCrabs()).isEqualTo(343605)
    }

    @Test
    fun `day7 - Puzzle2`() {
        assertThat(TheTreacheryOfWhales("day7-test.txt").alignCrabsTricky()).isEqualTo(168)
        assertThat(TheTreacheryOfWhales("day7.txt").alignCrabsTricky()).isEqualTo(96744904)
    }
}