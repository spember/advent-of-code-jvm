package pember.utils

/**
 * Many of these questions involve a grid of numbers and require iteration.
 */
open class PuzzleBoard<T>(protected val board: Array<Array<T>>) {

    init {
        // check that board lengths are uniform
        val firstSize = board[0].size
        for (x in board.indices) {
            if (firstSize != board[0].size) {
                throw IllegalStateException("All rows in the board must be the same size")
            }
        }
    }

    // methods for iterating

    fun at(location: Pair<Int, Int>): T = board[location.first][location.second]


    fun toSequence(): Sequence<Pair<T, Pair<Int, Int>>> = sequence {
        for (x in board.indices) {
            for (y in board[0].indices) {
                yield (board[x][y] to Pair(x,y))
            }
        }
    }

    fun findAdjacent(target: Pair<Int, Int>, includeDiagonals:Boolean=true ): List<Pair<Int, Int>> {
        val basic = mutableListOf(
            target.first to target.second-1, // down
            target.first-1 to target.second, // left
            target.first to target.second+1, // up
            target.first+1 to target.second, // right
        )
        if (includeDiagonals) {
            basic.addAll(listOf(
                target.first-1 to target.second-1,
                target.first-1 to target.second+1,
                target.first+1 to target.second+1,
                target.first+1 to target.second-1
            ))
        }
        return basic
            .filter { it.first >= 0 && it.first < board.size }
            .filter { it.second >= 0 && it.second < board[0].size }
    }

    fun modifyMap(fn: (T) -> T?) {
        toSequence().map {(value, position) ->
            fn(value)?.let { updatedValue -> board[position.first][position.second] = updatedValue}
        }
    }

    fun display(delimiter: String = "") {
        for (y in board[0].indices) {
            for(x in board.indices) {
                print("${board[x][y]}${delimiter}")
            }
            print("\n")
        }
        print("\n")
    }

    fun displayRows(delimiter: String="") {
        for (x in board.indices) {
            println(board[x].joinToString(delimiter))
        }
    }
}