package pember.aoc2021

import pember.utils.YearlyFileReader
import java.util.*

class SyntaxScoring(private val fileName: String) {

    fun findFirst(): Int = parse()
            .map {findFirstError(it)}
            .map { corruptedValues[it]}
            .filter { it != null }
            .map {it!!.toInt()}
            .sum()

    fun findMiddleScoreOfCompleted(): Long = parse()
            .filter {findFirstError(it) == INCOMPLETE}
            // now have the incomplete lines
            .map {completeLine(it)}
            .map {scoreLine(it)}
            .sorted()
            .toList()
            .let {it[it.size/2]}

    private fun findFirstError(chars: CharArray): Char {
        val searchQueue = LinkedList<Char>()
        for (c in chars) {
            if (c in symbolPairs.keys) {
                searchQueue.push(c)
            } else if (searchQueue.isEmpty()) {
//                println("Closed without an open! ${c}")
                return c
            } else if (symbolPairs[searchQueue.peek()]!! != c) {
//                println("Found invalid match! expected ${symbolPairs[searchQueue.peek()]}, but found ${c} instead")
                return c
            } else {
                searchQueue.poll()
            }
        }
        return INCOMPLETE
    }

    private fun completeLine(line: CharArray): List<Char> {
        val searchQueue = LinkedList<Char>()
        for (c in line) {
            if (c in symbolPairs.keys) {
                searchQueue.push(c)
            } else {
                // at this point we know that the line is not corrupted
                searchQueue.poll()
            }
        }
        // now to balance
        return searchQueue.map { symbolPairs[it]!! }
    }

    // whoops it was a long
    private fun scoreLine(line: List<Char>): Long =
        line.fold(0L) {score, c -> completedValues[c]!! + score * 5}


    private fun parse(): Sequence<CharArray> =
        YearlyFileReader(2021).readNonEmptyLines(fileName).map {it.toCharArray()}

    companion object {

        private const val INCOMPLETE = '-'

        private val symbolPairs: Map<Char,Char> = mapOf(
            Pair('(', ')'),
            Pair('[', ']'),
            Pair('{', '}' ),
            Pair('<', '>')
        )

        private val corruptedValues: Map<Char, Int> = mapOf(
            Pair(')', 3),
            Pair(']', 57),
            Pair('}', 1197),
            Pair('>',25137)
        )
        private val completedValues: Map<Char, Int> = mapOf(
            Pair(')', 1),
            Pair(']', 2),
            Pair('}', 3),
            Pair('>', 4)
        )
    }
}