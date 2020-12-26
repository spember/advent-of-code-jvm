package pember.aoc2020

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 *
 */
class Part3Test {
    @Test
    fun `day13-puzzle1`() {
        val firstTest = ShuttleSearch("day13-test.txt")
        assertThat(firstTest.findEarliestTime()).isEqualTo(295)
        val puzzleResult = ShuttleSearch("day13.txt")
        assertThat(puzzleResult.findEarliestTime()).isEqualTo(156)
    }

    @Test
    fun `day13-puzzle2`() {
        val firstTest = ShuttleSearch("day13-test.txt")
        assertThat(firstTest.findSubsequentTimes()).isEqualTo(1068781L)
        val secondTest = ShuttleSearch("day13-test2.txt")
        assertThat(secondTest.findSubsequentTimes()).isEqualTo(779210L)
        val puzzleResult = ShuttleSearch("day13.txt")
        assertThat(puzzleResult.findSubsequentTimes()).isEqualTo(404517869995362)
    }

    @Test
    fun `day14-puzzle1`() {
        val firstTest = DockingData("day14-test.txt")
        firstTest.initializeV1()
        assertThat(firstTest.sumValuesInMemory()).isEqualTo(165)

        val puzzleData = DockingData("day14.txt")
        puzzleData.initializeV1()
        assertThat(puzzleData.sumValuesInMemory()).isEqualTo(7440382076205)
    }

    @Test
    fun `day14-puzzle2`() {
        val first = DockingData("day14-test2.txt")
        first.initializeV2()
        val result = first.sumValuesInMemory()

        assertThat(result).isEqualTo(208)

        val second = DockingData("day14.txt")
        second.initializeV2()
        val result2 = second.sumValuesInMemory()

        assertThat(result2).isEqualTo(4200656704538)
    }

    @Test
    fun `day15-puzzle1`() {
        val test = RambunctiousRecitation.basicSpeaking(intArrayOf(0,3,6), 10)
        assertThat(test).isEqualTo(0)
        assertThat(RambunctiousRecitation.basicSpeaking(intArrayOf(0, 3, 6), 2020)).isEqualTo(436)

        assertThat(RambunctiousRecitation.basicSpeaking(intArrayOf(6, 19, 0, 5, 7, 13, 1), 2020))
            .isEqualTo(468)
    }

    @Test
    fun `day15-puzzle2`() {
        // brute force! but it worked immediately when changing to the second puzzle, likely given I was using a
        // Map instead of a list or some other such to store number history and search for previous usages
        //
        // Even still, this approach can crash the run time by running out of Heap, although it works when run
        // inside of an IDE. Clearly, there's a much more efficient solution here... but this works well enough to
        // earn the correct answer in deterministic (~2-3 seconds) time
//        val test = RambunctiousRecitation.basicSpeaking(intArrayOf(3,2,1), 30000000)
//        assertThat(test).isEqualTo(18)
//        Truth.assertThat(RambunctiousRecitation.basicSpeaking(intArrayOf(6, 19, 0, 5, 7, 13, 1), 30000000))
//            .isEqualTo(1801753)
    }

    @Test
    fun `day16-puzzle1`() {
        val tester = TicketTranslation("day16-test.txt")
        assertThat(tester.generalErrorScanningRate()).isEqualTo(71)

        val puzzle = TicketTranslation("day16.txt")
        assertThat(puzzle.generalErrorScanningRate()).isEqualTo(22000)
    }

    @Test
    fun `day16-puzzle2`() {
        val tester = TicketTranslation("day16-test2.txt")
        assertThat(tester.decodeFields()).isEqualTo(1)


        val puzzle = TicketTranslation("day16.txt")
        val result = puzzle.decodeFields() // I got this wrong at first
        assertThat(result).isNotEqualTo(668844166853)
        assertThat(result).isEqualTo(410460648673)
    }

    @Test
    fun `day17-puzzle1`() {
//        val testGrid = ConwayCubes("day17-test.txt")
//        testGrid.printSlices()
//        assertThat(testGrid.countActiveCubes()).isEqualTo(5)
////        assertThat(testGrid.countActiveNeighbors(ConwayCubes.ConwayPoint(0,0,0))).isEqualTo(1)
//        assertThat(testGrid.countActiveNeighbors(ConwayCubes.ConwayPoint(0,1,1))).isEqualTo(5)
//
//        for (i in 0..5) {
//            testGrid.runCycle()
//        }
////        testGrid.printSlices()
//        assertThat(testGrid.countActiveCubes()).isEqualTo(112)

        val puzzle = ConwayCubes("day17.txt")
        for (i in 0..5) {
            puzzle.runCycle()
        }
        puzzle.printSlices()
        println("active: ${puzzle.countActiveCubes()}")
        assertThat(puzzle.countActiveCubes()).isEqualTo(372)

    }

    @Test
    fun `day17-puzzle2`() {
        val testGrid = ConwayHyperCubes("day17-test.txt")
        assertThat(testGrid.countActiveCubes()).isEqualTo(5)
        assertThat(testGrid.countActiveNeighbors(ConwayHyperCubes.HyperPoint(0, 0,0,0))).isEqualTo(1)
        assertThat(testGrid.countActiveNeighbors(ConwayHyperCubes.HyperPoint(0, 0,1,1))).isEqualTo(5)
        for (i in 0..5) {
            testGrid.runCycle()
        }
        assertThat(testGrid.countActiveCubes()).isEqualTo(848)

        val puzzle = ConwayHyperCubes("day17.txt")
        for (i in 0..5) {
            puzzle.runCycle()
        }

        println("active: ${puzzle.countActiveCubes()}")
        assertThat(puzzle.countActiveCubes()).isEqualTo(1896)
    }

    @Test
    fun `day18-puzzle1`() {
        assertThat(OperationOrder.evalulateSingleLine("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(71)
        assertThat(OperationOrder.evalulateSingleLine("1 + (2 * 3) + (4 * (5 + 6))")).isEqualTo(51)

        assertThat(OperationOrder.evalulateSingleLine("2 * 3 + (4 * 5)")).isEqualTo(26)
        assertThat(OperationOrder.evalulateSingleLine("5 + (8 * 3 + 9 + 3 * 4 * 3)")).isEqualTo(437)
        assertThat(OperationOrder.evalulateSingleLine("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).isEqualTo(12240)
        assertThat(OperationOrder.evalulateSingleLine("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).isEqualTo(13632)
        val result = OperationOrder.evalulateExpressions("day18-test.txt")
        assertThat(result).isEqualTo(71+26+437)

        val puzzleResult = OperationOrder.evalulateExpressions("day18.txt")
        assertThat(puzzleResult).isEqualTo(53660285675207)
    }

    @Test
    fun `day18-puzzle2`() {
        assertThat(OperationOrder.mergeTokensV2(listOf("1", "+", "2", "*", "3", "+", "4", "*", "5", "+","6"))).isEqualTo(231)
        assertThat(OperationOrder.mergeTokensV2(listOf("4", "*", "5"))).isEqualTo(20)
        assertThat(OperationOrder.mergeTokensV2(listOf("2", "*", "3", "+", "20"))).isEqualTo(2*23)

        assertThat(OperationOrder.evalulateSingleLine("1 + 2 * 3 + 4 * 5 + 6", false)).isEqualTo(231)
        assertThat(OperationOrder.evalulateSingleLine("1 + (2 * 3) + (4 * (5 + 6))", false)).isEqualTo(51)
        assertThat(OperationOrder.evalulateSingleLine("2 * 3 + (4 * 5)", false)).isEqualTo(46)
        assertThat(OperationOrder.evalulateSingleLine("5 + (8 * 3 + 9 + 3 * 4 * 3)", false)).isEqualTo(1445)

        val result = OperationOrder.evalulateExpressions("day18-test.txt", false)
        assertThat(result).isEqualTo(231+46+1445)

        val puzzleResult = OperationOrder.evalulateExpressions("day18.txt", false)
        assertThat(puzzleResult).isEqualTo(141993988282687)
    }
}
