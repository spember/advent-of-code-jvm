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

    @Test
    fun `Day11 - Puzzle1`() {
//        assertThat(DumboOctopus("day11-pretest.txt").countFlashes(2)).isEqualTo(20)
        assertThat(DumboOctopus("day11-test.txt").countFlashes(10)).isEqualTo(204)
        assertThat(DumboOctopus("day11-test.txt").countFlashes(100)).isEqualTo(1656)
        assertThat(DumboOctopus("day11.txt").countFlashes(100)).isEqualTo(1713)
    }
    @Test
    fun `Day11 - Puzzle2`() {
//        assertThat(DumboOctopus("day11-pretest.txt").countFlashes(2)).isEqualTo(20)
        assertThat(DumboOctopus("day11-test.txt").findAll()).isEqualTo(195)
        assertThat(DumboOctopus("day11.txt").findAll()).isEqualTo(502)

    }

    @Test
    fun `Day12 - Puzzle1`() {
        assertThat(PassagePathing.Cave("start")).isEqualTo(PassagePathing.Cave("start"))
        assertThat(PassagePathing.Cave("start")).isNotEqualTo(PassagePathing.Cave("end"))
        assertThat(PassagePathing("day12-test1.txt").countPaths()).isEqualTo(10)
        assertThat(PassagePathing("day12-test2.txt").countPaths()).isEqualTo(19)
        assertThat(PassagePathing("day12-test3.txt").countPaths()).isEqualTo(226)
        assertThat(PassagePathing("day12.txt").countPaths()).isEqualTo(5457)
    }

    @Test
    fun `Day12 - Puzzle2`() {
        assertThat(PassagePathing("day12-test1.txt").countPaths(true)).isEqualTo(36)
        assertThat(PassagePathing("day12-test2.txt").countPaths(true)).isEqualTo(103)
        assertThat(PassagePathing("day12-test3.txt").countPaths(true)).isEqualTo(3509)
        assertThat(PassagePathing("day12.txt").countPaths(true)).isEqualTo(128506)
    }

    @Test
    fun `Day13 - Puzzle1`() {
        assertThat(TransparentOrigami("day13-test.txt").foldToInstructions()).isEqualTo(17)
        assertThat(TransparentOrigami("day13.txt").foldToInstructions()).isEqualTo(842)
    }

    @Test
    fun `Day13 - Puzzle2`() {
        assertThat(TransparentOrigami("day13-test.txt").foldToInstructions(false)).isEqualTo(16)
        assertThat(TransparentOrigami("day13.txt").foldToInstructions(false)).isEqualTo(95)
    }
}