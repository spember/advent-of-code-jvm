package pember.aoc2021

import pember.utils.AocPuzzle21
import pember.utils.PuzzleBoard

class TransparentOrigami(val filename: String): AocPuzzle21(filename) {

    fun foldToInstructions(onlyFirst:Boolean = true): Int {
        val groups = this.reader().readLinesIntoGroups(filename)
        var paper = Paper(groups.take(1)
            .flatMap { line -> line.map {it.split(",")}.map { Pair(it[0].toInt(), it[1].toInt())} }
            .toList())

        val instructions = groups.last()
            .map { line -> line.replace("fold along", "").split("=") }
            .map {Pair(it[0].trim()[0], it[1].toInt())}

        println(instructions)
        if (onlyFirst) {
            paper = paper.fold(instructions[0])

        } else {
            instructions.forEach {
                paper = paper.fold(it)
            }
            // read!
            paper.display()
        }
        return paper.countDots()
    }

    private class Paper: PuzzleBoard<Char> {
        constructor(data: List<Pair<Int, Int>>): super(convert(data))
        constructor(updatedBoard: Array<Array<Char>>): super(updatedBoard)

        fun fold(instruction: Pair<Char, Int>): Paper {
            // split at y = 7. board[i][7] is gone. anything above is the same, anything below is transposed.
            // board 2
            return if (instruction.first == UP) {
                foldUp(instruction.second)
            } else {
                foldLeft(instruction.second)
            }
        }

        fun foldUp(foldRow: Int): Paper {
            // create a new board Array(size(x)) Array (row) {'.'}
            val updatedBoard = Array(board.size) { Array(foldRow) {EMPTY} }
            // copy board
            for (x in board.indices) {
                for (y in 0 until foldRow) {
                    updatedBoard[x][y] = board[x][y]
                }
            }
            // starting at fold line + 1 and going to the end of the old paper, put [x][foldRow+1+i] at [x][foldrow-1-i] if it's a #

            for (x in board.indices) {
                for (y in 1 until board[0].size-foldRow) {
                    if (board[x][foldRow+y] == DOT) {
                        updatedBoard[x][foldRow-y] = DOT
                    }
                }
            }
            return Paper(updatedBoard)
        }

        fun foldLeft(foldColumn: Int): Paper {
            val updatedBoard = Array(foldColumn){ Array(board[0].size) {EMPTY} }
            for (x in (0) until foldColumn) {
                for (y in board[0].indices) {
                    updatedBoard[x][y] = board[x][y]
                }
            }

            for (x in 1 until(board.size-foldColumn)) {
                for (y in board[0].indices) {
                    if (board[foldColumn+x][y] == DOT) {
                        updatedBoard[foldColumn-x][y] = DOT
                    }
                }
            }
            return Paper(updatedBoard)
        }

        fun countDots(): Int =
            toSequence().count { (value, _) -> value == DOT }

        companion object {
            private fun convert(data: List<Pair<Int, Int>>): Array<Array<Char>> {
                val board = Array(data.map { it.first }.maxOrNull()!!+1) {
                    Array(data.map {it.second}.maxOrNull()!!+1) { EMPTY }
                }
                println("Board dimensions are ${board.size} / ${board[0].size}")
                data.forEach { board[it.first][it.second] = DOT }
                return board
            }

            private const val UP = 'y'
            private const val DOT = '#'
            private const val EMPTY = '.'
        }
    }
}