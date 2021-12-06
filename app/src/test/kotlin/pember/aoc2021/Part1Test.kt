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
}