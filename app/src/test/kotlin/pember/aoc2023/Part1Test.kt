package pember.aoc2023

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Part1Test {

    @Test
    fun `Day1 - Trebuchet part 1`() {
        assertEquals(142, Trebuchet.part1("day1-test.txt"))
        assertEquals(55488, Trebuchet.part1("day1.txt"))
    }

    @Test
    fun `Day1 - Trebucet part 2`() {
        assertEquals(281, Trebuchet.part2("day1-test2.txt"))
        assertEquals(55614, Trebuchet.part2("day1.txt"))
    }

    @Test
    fun `Day2 - Cubes part1`() {
        assertEquals(8, CubeConundrum.countPossible("day2-test.txt"))
        assertEquals(2416, CubeConundrum.countPossible("day2.txt"))
    }

    @Test
    fun `Day 2 - Cubes part 2`() {
        assertEquals(2286, CubeConundrum.findMinimum("day2-test.txt"))
        assertEquals(63307, CubeConundrum.findMinimum("day2.txt"))
    }

    @Test
    fun `Day 3 - Gear Ratios part 1`(){
        assertEquals(4361, GearRatios.sumParts("day3-test.txt"))
        assertEquals(525119, GearRatios.sumParts("day3.txt"))
    }

    @Test
    fun `Day 4 - Gear Ratios part 2`() {
        assertEquals(467835, GearRatios.findGears("day3-test.txt"))
        assertEquals(76504829, GearRatios.findGears("day3.txt"))
    }
}