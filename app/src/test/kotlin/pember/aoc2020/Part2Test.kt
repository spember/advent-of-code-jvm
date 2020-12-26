package pember.aoc2020

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * Lumping all of the tests in here for now
 */
class Part2Test {

    @Test
    fun `day7-puzzle1`() {
        val handy = HandyHaversacks("day7-test.txt")

        assertThat(handy.possibleBagsThatHold("shiny gold")).isEqualTo(4)

        val fullHandy = HandyHaversacks("day7.txt")
        val fullResult = fullHandy.possibleBagsThatHold("shiny gold")
        println("Full result = ${fullResult}")
        assertThat(fullResult).isEqualTo(257)
    }

    @Test
    fun `day7-puzzle2`() {
        val testHandy = HandyHaversacks("day7-test.txt")
        assertThat(testHandy.bagCountPerOneOf("dotted black")).isEqualTo(0)
        assertThat(testHandy.bagCountPerOneOf("shiny gold")).isEqualTo(32)

        val test2Handy = HandyHaversacks("day7-test2.txt")
        assertThat(test2Handy.bagCountPerOneOf("shiny gold")).isEqualTo(126)

        val fullHandy = HandyHaversacks("day7.txt")
        val fullResult = fullHandy.bagCountPerOneOf("shiny gold")
        println("Full result = ${fullResult}")
        assertThat(fullResult).isEqualTo(1038)
    }

    @Test
    fun `day8-puzzle1`() {
        val haltingTest = HandheldHalting("day8-test.txt")
            .searchForCycle()

        assertThat(haltingTest.accumulator).isEqualTo(5)
        val puzzleValue = HandheldHalting("day8.txt")
            .searchForCycle()


        println("Got value of ${puzzleValue.accumulator}")

        assertThat(puzzleValue.accumulator).isEqualTo(2058)

    }

    @Test
    fun `day8-puzzle2`() {

        val haltingTest = HandheldHalting("day8-test.txt")
            .searchForCycle()
            .brutelyFix()

        assertThat(haltingTest.accumulator).isEqualTo(8)

        val puzzleTest = HandheldHalting("day8.txt")
            .searchForCycle()
            .brutelyFix()

        println("Well, acc is ${puzzleTest.accumulator}")
        assertThat(puzzleTest.accumulator).isEqualTo(1000)

    }

    @Test
    fun `day9-puzzle1`() {
        val result = EncodingError.findFirstFailure(5, "day9-test.txt")
        println("result is ${result}")
        assertThat(result).isEqualTo(127)

        val puzzleResult = EncodingError.findFirstFailure("day9.txt")
        println("Result is ${puzzleResult}")
        assertThat(puzzleResult).isEqualTo(90433990)
    }

    @Test
    fun `day9-puzzle2`() {
        assertThat(EncodingError.findEncryptionWeakness("day9-test.txt", 127)).isEqualTo(62)

        val puzzleResult = EncodingError.findEncryptionWeakness("day9.txt", 90433990L)
        println("Result is ${puzzleResult}")
        assertThat(puzzleResult).isEqualTo(11691646)

    }

    @Test
    fun `day10-puzzle1`() {
        // first test
        val firstTest = AdapterArray.findSteps("day10-test1.txt")
        assertThat(firstTest).isGreaterThan(-1)

        val secondTest = AdapterArray.findSteps("day10-test2.txt")
        assertThat(secondTest).isEqualTo(220)

        val puzzleResult = AdapterArray.findSteps("day10.txt")
        assertThat(puzzleResult).isEqualTo(2232)
    }

    @Test
    fun `day10-puzzle2`() {
        val firstTest = AdapterArray.findAllPossibleSteps("day10-test1.txt")
        println("hmm = ${firstTest}")
        assertThat(firstTest).isGreaterThan(-1)

        val secondTest = AdapterArray.findAllPossibleSteps("day10-test2.txt")
        println("hmm = ${secondTest}")
        assertThat(secondTest).isEqualTo(19208)

        val puzzleResult = AdapterArray.findAllPossibleSteps("day10.txt")
        assertThat(puzzleResult).isEqualTo(173625106649344)
    }

    @Test
    fun `day11-puzzle1`() {
        val firstTest = SeatingSystem("day11-test.txt")

        firstTest.musicalChairs()
        assertThat(firstTest.countOccupied()).isEqualTo(37)

        val puzzleSeating = SeatingSystem("day11.txt")
        puzzleSeating.musicalChairs()
        assertThat(puzzleSeating.countOccupied()).isEqualTo(2329)
    }

    @Test
    fun `day11-puzzle2`() {
        val firstTest = SeatingSystem("day11-test.txt")
        firstTest.musicalChairs(false)
        assertThat(firstTest.countOccupied()).isEqualTo(26)

        val puzzleSeating = SeatingSystem("day11.txt")
        puzzleSeating.musicalChairs(false)
        assertThat(puzzleSeating.countOccupied()).isEqualTo(2138)
    }

    @Test
    fun `day12-puzzle1`() {
        val firstTest = RainRisk("day12-test.txt")
        firstTest.sail()
        assertThat(firstTest.manhattanDistance()).isEqualTo(25)

        val puzzleResult = RainRisk("day12.txt")
        puzzleResult.sail()
        assertThat(puzzleResult.manhattanDistance()).isEqualTo(796)
    }

    @Test
    fun `day12-puzzle2`() {
        val firstTest = RainRisk("day12-test.txt")
        firstTest.sailWithWaypoint()
        assertThat(firstTest.manhattanDistance()).isEqualTo(286)

        val puzzleResult = RainRisk("day12.txt")
        puzzleResult.sailWithWaypoint()
        assertThat(puzzleResult.manhattanDistance()).isEqualTo(39446)
    }
}
