package pember.aoc2022

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import com.google.gson.Gson


class Part2Test {

    @Test
    fun `Day 9 - Rope Bridge 1`() {
        assertThat(RopeBridge("day9-test.txt").countTailPositions()).isEqualTo(13)
        assertThat(RopeBridge("day9.txt").countTailPositions()).isEqualTo(5960)
    }

    @Test
    fun `Day 9 - Rope Bridge 2`() {
        assertThat(RopeBridge("day9-test.txt").countTailPositions(10)).isEqualTo(1)
        assertThat(RopeBridge("day9-test2.txt").countTailPositions(10)).isEqualTo(36)
        assertThat(RopeBridge("day9.txt").countTailPositions(10)).isEqualTo(2368)
        // the above is wrong, but I don't know why. the first two tests pass
        // not 2179 or 2368 .. 2300 is too low
        // only one star today
    }

    @Test
    fun `Day 10 - Signal Scanner 1 & 2`() {
        assertThat(CathodeRayTube("day10-test.txt").findSignalStrength()).isEqualTo(13140)
        assertThat(CathodeRayTube("day10.txt").findSignalStrength()).isEqualTo(12740)
        // draws: RBPARAGF
    }

    @Test
    fun `Day 11 - MonkeyBusiness `() {
        assertThat(MonkeyInTheMiddle("day11-test.txt").calculateMonkeyBusiness(20)).isEqualTo(10605)
        assertThat(MonkeyInTheMiddle("day11.txt").calculateMonkeyBusiness(20)).isEqualTo(78960)
    }

    @Test
    fun `Day 11 - MonkeyBusiness 2`() {
        assertThat(MonkeyInTheMiddle("day11-test.txt").crazyMonkeyBusiness(10000)).isEqualTo(2713310158)
        assertThat(MonkeyInTheMiddle("day11.txt").crazyMonkeyBusiness(10000)).isEqualTo(14561971968)
    }

    @Test
    fun `Day 12 - Hill CLimbing Steps` () {
        assertThat(HillClimbing("day12-test.txt").findShortestSteps()).isEqualTo(31)
        assertThat(HillClimbing("day12.txt").findShortestSteps()).isEqualTo(423)
    }

    @Test
    fun `Day 12 - Hill CLimbing Steps 2` () {
        assertThat(HillClimbing("day12-test.txt").fromAllAs()).isEqualTo(29)
        assertThat(HillClimbing("day12.txt").fromAllAs()).isEqualTo(416)
    }

    @Test
    fun `Day 13 - Compare checks`() {
        assertThat(DistressSignal("day13-test.txt").sumRightOrder()).isEqualTo(13)
        // this is not the right answer, although it passes the test input ... hmmm
        assertThat(DistressSignal("day13.txt").sumRightOrder()).isEqualTo(5066)
    }

}