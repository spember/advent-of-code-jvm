package pember.aoc2023

object GearRatios: Aoc2023(2) {

    fun sumParts(fn: String): Int {
        val grid = reader.readLinesIntoCharGrid(fn)
        val partNums = mutableListOf<Int>()
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                val space = grid[y][x]
                if (!space.isDigit() && space != EMPTY) {
                    partNums.addAll(findLocalParts(y, x, grid))
                }
            }
        }
        println("Found a sum of ${partNums.sum()} on ${partNums.size} used parts")
        return partNums.sum()
    }

    fun findGears(fn: String): Long {
        val grid = reader.readLinesIntoCharGrid(fn)
        val ratios = mutableListOf<Long>()
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                val space = grid[y][x]
                if (space == GEAR) {
                    val localParts = findLocalParts(y,x,grid)
                    if (localParts.size == 2) {
                        ratios.add(localParts.first().toLong() * localParts.last())
                    }
                }
            }
        }
        return ratios.sum()
    }

    private fun findLocalParts(y: Int, x: Int, grid: Array<CharArray>): Set<Int> =
        generateAdjacentPoints(y,x, grid.size, grid[y].size)
            .filter { grid[it.first][it.second].isDigit() }
            .map { extractNum(it.first, it.second, grid) }
            .toSet()

    private fun extractNum(y: Int, x: Int, grid: Array<CharArray>): Int {
        var numStr = "${grid[y][x]}"
        for (l in x-1 downTo 0) {
            if (grid[y][l].isDigit()) {
                numStr = grid[y][l] + numStr
            } else {
                break
            }

        }
        for (r in x+1 until grid[y].size) {
            if (grid[y][r].isDigit()) {
                numStr += grid[y][r]
            } else {
                break
            }
        }
        return Integer.parseInt(numStr)
    }

    private fun generateAdjacentPoints(y: Int, x: Int, yMax: Int, xMax: Int): List<Pair<Int, Int>> {
        return mutableListOf(
            //north/south
            y -1  to x,
            y + 1 to x,
            y to x - 1,
            y to x + 1,

            y - 1 to x -1,
            y - 1 to x + 1,
            y + 1 to x -1,
            y + 1 to x + 1
        )
            .filter { it.first in 0 until yMax }
            .filter { it.second in 0 until xMax }
            .toList()
    }

    private const val EMPTY = '.'
    const val GEAR = '*'
}