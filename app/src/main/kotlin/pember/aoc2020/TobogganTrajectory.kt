package pember.challenges.aoc.aoc2020

import pember.utils.FileReader

class TobogganTrajectory(private val inputFile: String) {

    private val TREE = '#'

    fun findTreesInFixedSlope(): Long  =
        ski(loadSlopes(inputFile), 3, 1)

    fun multiplyTreesIHit(): Long {
        val moves = listOf (
            Pair(1,1),
            Pair(3,1),
            Pair(5,1),
            Pair(7,1),
            Pair(1,2)
        )
        val slope = loadSlopes(inputFile)
        return moves.map { ski(slope, it.first, it.second) }
            .reduce {acc, i ->  acc*i}
    }

    private fun ski(slope: Array<CharArray>, rightMove: Int, downMove:Int): Long {
        var numTrees = 0L
        var currentAltitude = 0
        var currentRightness = 0 // idk what else to call how far right I am

        while (currentAltitude < slope.size ) {
            if (slope[currentAltitude][currentRightness] == TREE) {
                // println("hit at ${currentAltitude}, ${currentRightness}")
                numTrees++
            }
            currentAltitude += downMove
            currentRightness = calculateRightness(currentRightness, rightMove, slope[0].size)
        }

        return numTrees
    }

    fun calculateRightness(currentRightness: Int, rightMove: Int, maxWidth: Int): Int {
        var nextRightness = currentRightness + rightMove
        if (nextRightness >= maxWidth) {
            nextRightness = nextRightness - maxWidth
        }
        return nextRightness
    }

    private fun loadSlopes(inputFile: String): Array<CharArray> =
        FileReader.readLines(inputFile)
            .filter { it.isNotBlank() }
            .toList()
            .map {
                it.toCharArray()
            }.toTypedArray()
}
