package pember.aoc2021

import pember.utils.AocPuzzle21
import pember.utils.PuzzleBoard
import java.util.*

class Chiton(private val fileName: String): AocPuzzle21(fileName) {

    fun findLowestRisk(): Int {
        val paths = parse();
        paths.displayRows()
        return paths.findPath()
    }

    fun expandedRisk(): Int {
        // grab the initial board, then expand 5x AND THEN find the path
        val expanded = ChitonPath(parse().expand())
        return expanded.findPath()
    }


    private fun parse(): ChitonPath =
        ChitonPath(readLines().map { line -> line.map {Character.getNumericValue(it)}.toTypedArray()}
            .toList().toTypedArray())


    private class ChitonPath(data: Array<Array<Int>>): PuzzleBoard<Int>(data) {

        fun findPath(): Int {
            // move through the board, keeping tracking of places you visited and look for the lowest risk from point
            // to point. Reading this later it looks kind of brute force but hey

            // track the global risk of moving, the idea is to carve a min path through this board based on risks
            val cumulativeRisk = Array(this.board.size) {Array(this.board[0].size) {Integer.MAX_VALUE}}

            // keep a priority queue of the easiest way through this mess
            val toVisit = PriorityQueue<Pair<Int, Int>> { (pY, pX), (rY, rX) ->
                cumulativeRisk[pY][pX].compareTo(cumulativeRisk[rY][rX])
            }
            // and also track those we looked at
            val examined = mutableSetOf(0 to 0)

            cumulativeRisk[0][0] = 0
            toVisit.add(0 to 0)

            while(toVisit.isNotEmpty()) {
                val currentPosition = toVisit.poll().also { examined.add(it) } //I love also
                this.findAdjacent(currentPosition, false).forEach { (nx, ny) ->
                    if (!examined.contains(nx to ny)) {
                        val updatedRisk = cumulativeRisk[currentPosition.first][currentPosition.second] + board[nx][ny]
                        if (updatedRisk < cumulativeRisk[nx][ny]) {
                            cumulativeRisk[nx][ny] = updatedRisk
                            toVisit.add(nx to ny)
                        }
                    }

                }
            }

            return cumulativeRisk.last().last()
        }

        fun expand():Array<Array<Int>> {
            //create a List of List of Boards, then smooooosh
            val collectedBoards = mutableListOf(makeBoards(board))

            (1..4).map { it ->
                // here
                collectedBoards.add(makeBoards(collectedBoards[it-1][1]))
            }
            // and join
            val baseX = board.size
            val baseY = board.first().size
            val expanded = Array(board.size*5) {Array(board.first().size*5) {0} }
            (0..4).forEach { bigX ->
                (0..4).forEach {bigY ->
                    // basically we're iterating over a 4 dimensional construct... an array of array of boards,
                    // copying the values into a flattened mega-board
                    for (x in collectedBoards[bigX][bigY].indices){
                        for (y in collectedBoards[bigX][bigY][x].indices){
                            expanded[(bigX*baseX)+x][(bigY*baseY)+y] = collectedBoards[bigX][bigY][x][y]
                        }
                    }
                }
            }
            return expanded
        }

        fun makeBoards(startingBoard: Array<Array<Int>>): List<Array<Array<Int>>> {
            val boards = mutableListOf(startingBoard)
            (1..4).map {
                boards.add(increment(boards.get(it-1)))
            }
            return boards
        }

        private fun increment(old: Array<Array<Int>>): Array<Array<Int>> {
            val nextBoard = Array(old.size) {Array(old.size) {0}}
            for(x in old.indices){
                for (y in old[0].indices) {
                    nextBoard[x][y] = old[x][y]+1
                    if (nextBoard[x][y] > 9){
                        nextBoard[x][y] = 1
                    }
                }
            }
            return nextBoard
        }
    }
}