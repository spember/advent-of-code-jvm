package pember.aoc2021

import pember.utils.AocPuzzle21
import java.util.*

class DumboOctopus(filename: String): AocPuzzle21(filename) {

    // grid of numbers
    // on step:
    // increment
    // if hits 10, record a flash, find all adjacent, increment
    // if energy is 10, no increments any more
    // once no flashes, do a reset of all 10's -> 0

    fun countFlashes(steps: Int): Int {
        val board = parse()
        val sum = 1.rangeTo(steps)
            .map {executeStep(board)}
            .sum()
        display(board)
        return sum
    }

    fun findAll(): Int {
        var rounds = 0
        val board = parse()
        while (true) {

            rounds +=1
            if (executeStep(board) == 100) {
                break
            }
        }
        return rounds
    }


    /**
     * execute a step for the board, counting the flashes
     */
    private fun executeStep(board: Array<Array<Int>>): Int {
        // increment all
        iterate(board) {pos, value ->
            board[pos.first][pos.second] = value + 1
        }
        // scan for 10's. If any, put in queue. keep looping until no changes
        var flashCount = 0
        val flashQueue = LinkedList<Pair<Int, Int>>()

        findFlashers(board).forEach {
            flashCount += 1
            flashQueue.add(it)
        }

        // find all adjacent to the flashers
        while (flashQueue.isNotEmpty()) {
            findAdjacent(board, flashQueue.poll()).forEach {
                board[it.first][it.second] += 1
            }
            flashQueue.addAll(findFlashers(board).map {
                flashCount += 1
                it
            }.toList())
        }

        // reset
        iterate(board) {pos, value ->
            if (value > 9) {
                board[pos.first][pos.second] = 0
            }
        }
//        display(board)
        return flashCount
    }

    private fun iterate(board: Array<Array<Int>>, logic: (Pair<Int,Int>, Int) -> Unit) {
        for (x in board.indices) {
            for (y in board[0].indices) {
                logic(x to y, board[x][y])
            }
        }
    }

    private fun stream(board: Array<Array<Int>>): Sequence<Pair<Pair<Int, Int>, Int>> =
        sequence {
            for (x in board.indices) {
                for (y in board[0].indices) {
                    yield(Pair(x to y, board[x][y]))
                }
            }
        }

    private fun findFlashers(board: Array<Array<Int>>): List<Pair<Int, Int>> =
        stream(board)
            .toList()
            .filter { (_, value) -> value == 10 }
            .map {(pos, _) ->
                board[pos.first][pos.second] += 1
                pos
            }

    private fun findAdjacent(board: Array<Array<Int>>, pos: Pair<Int, Int>): List<Pair<Int, Int>> =
        listOf(
            pos.first-1 to pos.second-1,// down
            pos.first to pos.second-1,
            pos.first-1 to pos.second, // left
            pos.first-1 to pos.second+1,
            pos.first to pos.second+1, // up
            pos.first+1 to pos.second+1,
            pos.first+1 to pos.second, // right
            pos.first+1 to pos.second-1
        )
            .filter { it.first >= 0 && it.first < board.size }
            .filter { it.second >= 0 && it.second < board[0].size }

    private fun display(board: Array<Array<Int>>) {
        var x = 0
        iterate(board) {pos, value ->
            if(x != pos.first) {
                x = pos.first
                print("\n")
            }
            print(value)
        }
    }

    private fun parse(): Array<Array<Int>> = readLines()
            .map {
                it.map {c -> Character.getNumericValue(c) }.toTypedArray()
            }
            .toList().toTypedArray()



}