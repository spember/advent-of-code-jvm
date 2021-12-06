package pember.aoc2021

import pember.utils.YearlyFileReader
import java.util.*

class GiantSquid(private val fileName: String) {

    private val reader = YearlyFileReader(2021)

    fun findWinningBoardScore(): Int {
        val (scoreQueue, boards) = assembleBoards()

        var bingo: Board? = null
        var currentScore = 0
        while (bingo == null && scoreQueue.isNotEmpty()) {
            currentScore = scoreQueue.poll()
            boards.forEach {it.mark(currentScore)}
            bingo = boards.firstOrNull { it.hasBingo() }
        }

        return if (bingo == null) {
            println("No bingo!")
            0
        } else {
            bingo.findUnmarkedSpaceValues().sum() * currentScore
        }
    }

    fun findLosingBoardScore(): Int {
        val (scoreQueue, boards) = assembleBoards()

        var remainingBoards = boards.toList()
        var currentScore = 0

        while (remainingBoards.size > 1 && scoreQueue.isNotEmpty()) {
            currentScore = scoreQueue.poll()
            boards.forEach {it.mark(currentScore)}
            remainingBoards = boards.filter { !it.hasBingo() }
        }

        val remainingBoard = remainingBoards.first()
        remainingBoard.display()

        while(!remainingBoard.hasBingo() && scoreQueue.isNotEmpty()) {
            currentScore = scoreQueue.poll()
            remainingBoard.mark(currentScore)
        }
        remainingBoard.display()
        return remainingBoard.findUnmarkedSpaceValues().sum()*currentScore

    }

    private fun assembleBoards(): Pair<Queue<Int>, List<Board>> {
        val lines = reader.readLinesIntoGroups(fileName)

        val scoreQueue =  LinkedList(lines.take(1).first().flatMap {raw ->
            raw.split(",").map { it.toInt() }
        })

        val boards = lines.toList()
            .drop(1)
            .map { Board(it) }

        return Pair(scoreQueue, boards)
    }

    class Board(assignedSpaces: List<String>) {
        private var spaces: Array<Array<Space>> = Array(5) { emptyArray()}

        init {
            assignedSpaces.map { raw->
                raw.trim()
                    .split("\\s+".toRegex())
                    .map { Space(it.toInt()) }
            }
                .forEachIndexed { index, list -> spaces[index] = list.toTypedArray() }
        }

        fun mark(value: Int): Board {
            spaces.forEach { row ->
                row.forEach { space ->
                    if (space.value == value) {
                        space.called = true
                    }
                }
            }
            return this
        }

        fun display() {
            spaces.forEach { row ->
                println(row.map {it.toString()})
            }
            println("")
        }

        fun hasBingo(): Boolean = !findBingo().isNullOrEmpty()

        /**
         * if a row or column is found with a bingo, return the values
         */
        fun findBingo(): List<Int>? {
            // create a big list of the values in the columns and rows, then look for a bingo
            val checks = (spaces.indices).map { getRow(it) }
                .plus(spaces.map { it.toList() })
                .filter { it.size == it.filter { space->space.called }.size }

            return checks.firstOrNull()?.map { it.value }
        }

        fun findUnmarkedSpaceValues(): Set<Int> =
            spaces.flatMap { it.toList() }
                .filter { !it.called }
                .map { it.value }
                .toSet()

        private fun getRow(column: Int): List<Space> = spaces.map { row -> row[column] }

        private class Space(val value: Int) {
            var called = false

            override fun toString(): String {
                return "$value($called)"
            }
        }
    }
}