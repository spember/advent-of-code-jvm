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
}