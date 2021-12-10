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

    @Test
    fun `Day10 - Puzzle1`() {
        assertThat(SyntaxScoring("day10-test.txt").findFirst()).isEqualTo(26397)
        assertThat(SyntaxScoring("day10.txt").findFirst()).isEqualTo(240123)
    }

    @Test
    fun `Day10 - Puzzle2`() {
        assertThat(SyntaxScoring("day10-test.txt").findMiddleScoreOfCompleted()).isEqualTo(288957)
        assertThat(SyntaxScoring("day10.txt").findMiddleScoreOfCompleted()).isEqualTo(3260812321)
    }
}