package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 11
 * https://adventofcode.com/2020/day/11
 */
class SeatingSystem(inputFile: String) {
    private val board = mutableListOf<MutableList<Seat>>()
    // down, right
    init {
        FileReader.readLines(inputFile)
            .filter { it.isNotEmpty() }
            .map { row -> row
                .toCharArray()
                .fold(mutableListOf<Seat>()) { currentRow, char ->
                    currentRow.add(Seat(char))
                    currentRow
                }
            }
            .forEach { board.add(it) }
        println("Initial State:")
        drawBoard()
    }

    fun musicalChairs(onlyAdjacent: Boolean = true): SeatingSystem {
        while (true) {
            val transitioned = runIteration(onlyAdjacent)
//            drawBoard() // debug board state
            if (!transitioned) {
                println("made it!")
                break
            }
        }
        drawBoard()
        return this
    }


    fun countOccupied(): Int = board.map { row ->
        row.filter { seat -> seat.isOccupied() }
            .count()
    }
        .sum()


    /**
     * perform a single run of choosing seats. Returns true if ANY seats transitioned state
     */
    private fun runIteration(onlyAdjacent:Boolean=true): Boolean {
        for (row in 0 until board.size) {
            for (col in 0 until board[row].size) {
                if (onlyAdjacent) {
                    board[row][col].decide(findAdjacentdOccupiedSeatCount(row, col), 4)
                } else {
                    board[row][col].decide(findVisableOccupiedSeatCount(row, col), 5)
                }

            }
        }
        return board.map { row -> row.map {
                seat -> seat.transition()
            }
            .filter { it }
            .count()
        }
            .sum() != 0
    }

    private fun drawBoard(): SeatingSystem {
        board.forEach { row ->
            row.forEach { print(it) }
            println("")
        }

        return this
    }

    private fun findAdjacentdOccupiedSeatCount(row: Int, col: Int): Int =
        DIRECTIONS. map { row + it.first to col + it.second }
            .filter { it.first >=0 && it.first < board.size }
            .filter { it.second >=0 && it.second < board[0].size}
            .filter { board[it.first][it.second].isOccupied()}
            .count()

    private fun findVisableOccupiedSeatCount(row: Int, col: Int): Int =
        DIRECTIONS.map { findSeatInDirection(row, col, it.first, it.second) }
            .filterNotNull()
            .filter { board[it.first][it.second].isOccupied()}
            .count()

    private fun findSeatInDirection(row: Int, col:Int, rowTrajectory: Int, colTrajectory: Int): Pair<Int, Int>? {

        var currentRow = row
        var currentCol = col
        while(true) {
            currentRow += rowTrajectory
            currentCol += colTrajectory
            if (currentRow < 0 || currentRow >= board.size) {
                return null
            }
            if (currentCol < 0 || currentCol >= board[0].size) {
                return null
            }
            if (board[currentRow][currentCol].isSeat()) {
                return currentRow to currentCol
            }
        }
    }

    private class Seat(initialState: Char) {

        var currentState = initialState
            private set

        // the state to transition to
        private var pendingState: Char = initialState


        /**
         * The seat is empty; a Person would sit here
         */
        fun isEmpty() = currentState == EMPTY

        fun isSeat() = currentState == EMPTY || currentState == OCCUPIED

        fun isOccupied() = currentState == OCCUPIED

        fun decide(numAdjacentOccupiedSeats: Int, moveThreshold: Int): Seat {
            if (isSeat()) {
                if (isEmpty() && numAdjacentOccupiedSeats == 0){
                    pendingState = '#'

                } else if (isOccupied() && numAdjacentOccupiedSeats >= moveThreshold) {
                    pendingState = 'L'
                }
            }
            return this
        }

        /**
         * Initiate the transition of the Seat. Returns True if the seat changed
         */
        fun transition(): Boolean {
            val transitioned = currentState != pendingState
            currentState = pendingState
            return transitioned
        }

        override fun toString(): String {
            return "${currentState}"
        }

        companion object {
            private const val EMPTY = 'L'
            private const val FLOOR = '.'
            private const val OCCUPIED = '#'

        }
    }

    companion object {
        private val DIRECTIONS = listOf(
            -1 to -1,
            -1 to 0,
            -1 to 1,
            0 to -1,
            0 to 1,
            1 to -1,
            1 to 0,
            1 to 1
        )
    }
}
