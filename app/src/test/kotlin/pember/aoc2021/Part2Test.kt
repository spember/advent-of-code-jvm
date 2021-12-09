package pember.aoc2021

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Part2Test {

    @Test
    fun `Day9 - Puzzle1`() {
        assertThat(SmokeBasin("day9-test.txt").lowPointRisk()).isEqualTo(15)
        assertThat(SmokeBasin("day9.txt").lowPointRisk()).isEqualTo(465)
    }

    @Test
    fun `Day9 - Puzzle2`() {
        assertThat(SmokeBasin("day9-test.txt").largestBasins(3)).isEqualTo(1134)
        assertThat(SmokeBasin("day9.txt").largestBasins(3)).isEqualTo(1269555)
    }
}