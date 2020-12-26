package pember.challenges.aoc.aoc2020

import pember.utils.FileReader

object BinaryBoarding {
    fun findHighestSeatId(inputFile:String): Int =
        obtainSeatIds(inputFile)
            // is there a better 'max' functional way to do this?
            .fold(0) {currentMax, id ->
                if (id > currentMax) { id }
                else { currentMax }
            }

    fun findMySeat(inputFile: String): Int {
        val seatIds = obtainSeatIds(inputFile)
            .toList()
            .sorted()
        // could do this in 0(n) by building an n-sized array of 1/0 or true/false, set seat id index as 1, then look
        // for the 0 value index but meh
        var myId = 0
        for (i in 1 until seatIds.size) {
            if (seatIds[i-1] +1 != seatIds[i]) {
                myId = seatIds[i-1]+1
            }
        }
        return myId
    }

    private fun obtainSeatIds(inputFile: String): Sequence<Int> =
        FileReader.readLines(inputFile)
            .asSequence()
            .filter { it.isNotEmpty() }
            .map { makeSenseOfThis(it) }
            .filter {it != null }
            .map {it!!.seatId}

    private val seatReg = "([BF]{7})([RL]{3})".toRegex()
    private val lowers = listOf('L', 'F')
    private const val ROW_MAX = 127
    private const val COLUMN_MAX = 7

    fun makeSenseOfThis(seatInput: String): Seat? =
        extractRowAndColumn(seatInput)?.let {
            Seat(
                row = search(it.first, 0 to ROW_MAX),
                column = search(it.second, 0 to COLUMN_MAX)
            )
        }

    private fun extractRowAndColumn(seatInput: String): Pair<CharArray, CharArray>? {
        return seatReg.matchEntire(seatInput)?.let {
            it.groupValues[1].toCharArray() to it.groupValues[2].toCharArray()
        }
    }

    /**
     * We could recurse but nah
     */
    private fun search(charArray: CharArray, initialRange: Pair<Int, Int>): Int =
        charArray
            .fold(initialRange){range, direction -> search(range, direction) }
            .second // seems to always be second due to rounding. don't want to futz with it

    private fun search(currentRange: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
        // find the mid point then take either upper or lower
        val middle: Int = (currentRange.first + currentRange.second) / 2
        // 0 + 127 / 2 =  63.5
        return if (direction in lowers) {
            currentRange.first to middle
        } else {
            middle to currentRange.second
        }
    }

    class Seat(val row: Int, val column: Int) {
        val seatId: Int = (row * 8) + column
    }
}
