package pember.aoc2020

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import pember.challenges.aoc.aoc2020.BinaryBoarding
import pember.challenges.aoc.aoc2020.CustomCustoms
import pember.challenges.aoc.aoc2020.PassportDesk
import pember.challenges.aoc.aoc2020.PasswordCheck
import pember.challenges.aoc.aoc2020.ReportRepair
import pember.challenges.aoc.aoc2020.TobogganTrajectory

/**
 *
 */
class Part1Test {

    @Test
    fun `day1 - puzzle1`() {
        assertThat(ReportRepair.run()).isNotEqualTo(0)
    }

    @Test
    fun `day1 - puzzle2`() {
        assertThat(ReportRepair.run3()).isNotEqualTo(0)
    }

    @Test
    fun `day2 - puzzle1`() {
        assertThat(PasswordCheck.numValidRange("day2.txt")).isNotEqualTo(10)
    }

    @Test
    fun `day2 - puzzle2`() {
        assertThat(PasswordCheck.numValidPosition("day2.txt")).isEqualTo(747)
    }

    @Test
    fun `day3 - puzzle1`() {
        assertThat(TobogganTrajectory("day3.txt").findTreesInFixedSlope()).isEqualTo(156)
    }

    @Test
    fun `day3 - puzzle2`() {
        assertThat(TobogganTrajectory("day3.txt").multiplyTreesIHit()).isEqualTo(3521829480)
    }

    @Test
    fun `day4-puzzle1`() {
        assertThat(PassportDesk.porousCheck("day4-test.txt")).isEqualTo(2)
        val result = PassportDesk.porousCheck("day4.txt")
        println("result = ${result}")
        assertThat(result).isNotEqualTo(2)
    }

    @Test
    fun `day4-puzzle2`() {

        assertThat(PassportDesk.slightlyMoreRigorousCheck("day4-test2.txt")).isEqualTo(4)
        val check = PassportDesk.slightlyMoreRigorousCheck("day4.txt")
        println("check = ${check}")
        assertThat(check).isNotEqualTo(2)

    }

    @Test
    fun `day5-puzzle1`() {
        val seat = BinaryBoarding.makeSenseOfThis("BFFFBBFRRR")
        assertThat(seat).isNotNull()
        assertThat(seat!!.column).isEqualTo(7)
        assertThat(seat.row).isEqualTo(70)
        assertThat(seat.seatId).isEqualTo(567)

        val secondSeat = BinaryBoarding.makeSenseOfThis("FFFBBBFRRR")
        assertThat(secondSeat).isNotNull()
        assertThat(secondSeat!!.column).isEqualTo(7)
        assertThat(secondSeat.row).isEqualTo(14)
        assertThat(secondSeat.seatId).isEqualTo(119)

        val thirdSeat = BinaryBoarding.makeSenseOfThis("BBFFBBFRLL")
        assertThat(thirdSeat).isNotNull()
        assertThat(thirdSeat!!.column).isEqualTo(4)
        assertThat(thirdSeat.row).isEqualTo(102)

        val result = BinaryBoarding.findHighestSeatId("day5-test.txt")
        assertThat(result).isEqualTo(820)

        val final = BinaryBoarding.findHighestSeatId("day5.txt")
        println("highest = ${final}")
        assertThat(final).isEqualTo(944)
    }

    @Test
    fun `day5-puzzle2`() {
        val result = BinaryBoarding.findMySeat("day5.txt")
        println("My seat # is ${result}")
        assertThat(result).isGreaterThan(0)
    }

    @Test
    fun `day6-puzzle1`() {
        assertThat(CustomCustoms.findCountOfYesses("day6-test.txt")).isEqualTo(11)
        val result = CustomCustoms.findCountOfYesses("day6.txt")
        println("Count of yes: ${result}")
        assertThat(result).isEqualTo(6778)
    }

    @Test
    fun `day6-puzzle2`() {
        assertThat(CustomCustoms.findCountOfUnanimousAnswers("day6-test.txt")).isEqualTo(6)
        val result = CustomCustoms.findCountOfUnanimousAnswers("day6.txt")
        println("unanimous = ${result}")
        assertThat(result).isEqualTo(3406)
    }
}
