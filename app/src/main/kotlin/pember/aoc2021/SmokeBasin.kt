package pember.aoc2021

import pember.utils.YearlyFileReader
import java.util.*

class SmokeBasin(private val fileName: String) {

    fun lowPointRisk(): Int {
        val board = parse()
        var sum = 0
        board.forEachIndexed{x, row -> row.forEachIndexed { y, c ->
            val adjacent = findAdjacentCoordinates(board, x to y)
                .map {board[it.first][it.second]}
            if (adjacent.filter {c.depth < it.depth}.size == adjacent.size) {
                sum += (c.depth+1)
            }
        }}
        return sum
    }

    fun largestBasins(top: Int = 3): Int {
        val board = parse()
        val basins = mutableListOf<Int>()
        for (x in board.indices) {
            for (y in board[0].indices) {
                if (isExplorable(board[x][y])) {
                    basins.add(exploreBasin(board, x to y))
                }
            }
        }
        return basins.sorted().reversed().take(top).fold(1) {factor, size -> factor * size}
    }

    /**
     * Explore a basin given some starting point, returning the size of the basin.
     */
    private fun exploreBasin(board: List<List<Punkt>>, start: Pair<Int, Int>): Int {
        val basin = mutableListOf<Pair<Int,Int>>()
        val explorationQueue = LinkedList(listOf(start))
        while (explorationQueue.isNotEmpty()) {

            // if point is not scanned, scan it and add it to basin size
            val current = explorationQueue.poll()
            val punkt = board[current.first][current.second]
            if (isExplorable(punkt)) {
                punkt.scanned = true
                basin.add(current)
                explorationQueue.addAll(findAdjacentCoordinates(board, current))
            }
        }
        return basin.size
    }

    private fun isExplorable(punkt: Punkt): Boolean = !punkt.scanned && punkt.depth < 9

    private fun findAdjacentCoordinates(board: List<List<Punkt>>, point: Pair<Int, Int>): List<Pair<Int, Int>> =
        listOf(
            point.first-1 to point.second,
            point.first to point.second+1,
            point.first+1 to point.second,
            point.first to point.second-1
        )
            .filter { it.first >=0 && it.first < board.size  }
            .filter { it.second >=0 && it.second < board.first().size }

    private fun parse(): List<List<Punkt>> =
        YearlyFileReader(2021).readNonEmptyLines(fileName)
            .map {line -> line.toCharArray().map { Punkt(Character.getNumericValue(it)) }}
            .toList()

    private class Punkt(val depth: Int) {
        var scanned = false
    }
}