package pember.aoc2021

import pember.utils.YearlyFileReader

class BinaryDiagnostic(private val fileName: String) {

    fun diagnosticReport(): Int {
        val tallies = parse(fileName)
            .map { it.toCharArray() }
            .fold(mutableListOf()) { tallies: MutableList<PositionTally>, bits ->
                bits.forEachIndexed { index, c ->
                    // because the bit length is variable, grow as needed
                    // alternatively we could have scanned the first row and built up accordingly
                    if (tallies.size-1 < index) {
                        tallies.add(PositionTally())
                    }
                    tallies[index].tally(c)
                }
                tallies
            }
        // could add a context class that encapsulates both gamma and epsilon, but we're in a rush
        val gamma = tallies.map {it.most()}.toList().joinToString("")
        val epsilon = tallies.map {it.least()}.toList().joinToString("")
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
    }

    fun lifeSupportRating(): Int {
        // count all chars in position x, find the most, then
        // write a tally of a column in an arbitrary list
        // for each number, tally the posit

        val numbers = parse(fileName).toList()
        println("Full list = ${numbers.toList()}")
        val generator = reduceToLifeSupport(numbers, ONE) {tallyPosition -> tallyPosition.most()}
        val scrubber = reduceToLifeSupport(numbers, ZERO) {tallyPosition -> tallyPosition.least()}
        return generator * scrubber
    }

    private fun reduceToLifeSupport(numbers: List<String>, onEqualChoice: Char, tallyFn: (PositionTally)->Char ): Int {
        var position = 0
        var rating = numbers.toMutableList()
        while(rating.size > 1) {
            val tally = tallyPosition(rating, position)
            rating = rating
                .filter { if (tally.isEqual()) {
                    it[position] == onEqualChoice
                } else {
                    it[position] == tallyFn(tally)
                } }
                .toMutableList()
            position += 1
        }
        return Integer.parseInt(rating.first(), 2)
    }

    private fun tallyPosition(numbers: List<String>, position: Int): PositionTally =
        numbers.fold(PositionTally()) { tally, values -> tally.tally(values[position]) }

    private fun parse(fileName: String): Sequence<String> =
        YearlyFileReader(2021)
            .readNonEmptyLines(fileName)

    private class PositionTally() {
        private var zeroes = 0
        private var ones: Int = 0

        fun tally(bit: Char): PositionTally {
            when(bit) {
                ZERO -> zeroes += 1
                ONE -> ones += 1
                else -> {
                    println("There was a problem: $bit")
                }
            }
            return this
        }

        fun most(): Char {
            return if (zeroes > ones) {
                ZERO
            } else {
                ONE
            }
        }

        fun least(): Char {
            return if (zeroes < ones) {
                ZERO
            } else {
                ONE
            }
        }

        fun isEqual(): Boolean = zeroes == ones


    }

    companion object {
        private const val ZERO = '0'
        private const val ONE = '1'
    }
}