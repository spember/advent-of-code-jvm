package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 8
 *
 * https://adventofcode.com/2020/day/8
 */
class HandheldHalting(private val inputFile: String) {

    /*
        Basic idea:
        puzzle 1 -> track instruction calls in an array, if a non-zero value in the array is going to be replaced, we
        know that instruction was going to be called again. break and stop the cycle

        puzzle 2 -> madness to try and fix it... just brute force check all jmps and noops, change, and run again
     */

    var accumulator = 0
        private set

    private var instructions: Array<Instruction>
    private var callCount: Array<Int>

    init {
        instructions = FileReader.readLines(inputFile)
            .filter { it.isNotEmpty() }
            .map { Instruction(it) }
            .toList()
            .toTypedArray()
        callCount = Array(instructions.size) { 0 }
    }

    fun searchForCycle(): HandheldHalting {
        initializeGame()
        return this
    }

    /**
     * run the 'initialization' sequence, returning true if successful, or falsse if a cycle was detected
     */
    private fun initializeGame(): Boolean {
        // re-initialize the tracking globals
        accumulator = 0
        callCount = Array(instructions.size) { 0 }

        var currentIndex = 0
        var nextIndex = currentIndex

        while (true) {
            if (currentIndex >= instructions.size) {
                return true
            }
            if (callCount[currentIndex] == 1) {
                return false
            }
            val currentInstruction = instructions[currentIndex]
            when(currentInstruction.command) {
                NO_OP -> { nextIndex = currentIndex + 1}
                ACCUMULATE -> {
                    accumulator += currentInstruction.amount
                    nextIndex = currentIndex + 1
                }
                JUMP -> {
                    nextIndex = currentIndex + currentInstruction.amount
                }
            }

            callCount[currentIndex] = 1
            currentIndex = nextIndex
        }
    }

    // fix, scan backwards, looking using call call count for:
    // a noop of size-position
    // negative jmp

    fun brutelyFix(): HandheldHalting {
        for (i in callCount.size-1 downTo 0) {
            // i don't like the structure here but eh
            if (swapInstruction(i, JUMP, NO_OP)) {
                println("Fixed by changing ${i} to ${NO_OP}")
                break
            }
            if (swapInstruction(i, NO_OP, JUMP)) {
                println("Fixed by changing ${i} to ${JUMP}")
                break
            }
        }
        return this
    }

    private fun swapInstruction(position: Int, target: String, test: String): Boolean {
        var result = false
        if (instructions[position].command == target) {
            instructions[position].command = test
            result = initializeGame()
            instructions[position].command = target
        }
        return result

    }


    class Instruction(line: String) {
        var command: String
        val amount: Int

        init {
            val segments = line.split(" ")
            assert(segments.size == 2)
            command = segments[0]
            amount = convertAmount(segments[1])
        }

        private fun convertAmount(input: String): Int =
            if (input[0] == '+') {
                input.substring(1 until input.length).toInt()
            } else {
                input.toInt()
            }


        override fun toString(): String {
            return "${command} -> ${amount}"
        }

    }
    companion object {
        private const val NO_OP = "nop"
        private const val JUMP = "jmp"
        private const val ACCUMULATE = "acc"
    }
}
