package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 16
 *
 */
class TicketTranslation(private val fileInput: String) {
    /*
    It doesn't matter which position corresponds to which field; you can identify invalid nearby tickets by considering
    only whether tickets contain values that are not valid for any field. In this example, the values on the first
    nearby ticket are all valid for at least one field. This is not true of the other three nearby tickets: the values
    4, 55, and 12 are are not valid for any field. Adding together all of the invalid values produces your ticket
    scanning error rate: 4 + 55 + 12 = 71.

Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?
     */
    private val globalCheck = Array(1024) {0}
    private val rowDetails: MutableList<RowDetails> = mutableListOf()

    private val noteGroups: List<List<String>> = FileReader
        .readLinesIntoGroups(fileInput)
        .toList()

    init {
        println("Building global list of valid field values")
        noteGroups[0].map { extractRowDetails(it) }
            .filterNotNull()
            .forEach {
                rowDetails.add(it)
                for (i in it.firstRange) {
                    globalCheck[i] = 1
                }
                for (i in it.secondRange) {
                    globalCheck[i] = 1
                }
            }
    }

    fun generalErrorScanningRate(): Int =
        getTicketRows(2)
            .flatten()
            .filter {
                globalCheck[it] == 0
            }
            .sum()


    fun decodeFields(): Long {
        val myTicket = getTicketRows(1)
            .first()
        val validTickets = getTicketRows(2)
            .filter { ticket -> ticket
                    .filter {globalCheck[it] == 0}
                    .count() == 0
            }
            .toList()

        val options : MutableList<Set<String>> = mutableListOf()

        for (i in 0 until myTicket.size) {
            val possibleFields: MutableSet<String> = mutableSetOf()
            val columnRanges = mutableListOf(myTicket[i])
            validTickets.forEach { columnRanges.add(it[i]) }
//            println("current ranges are ${columnRanges}")
            possibleFields.addAll(rowDetails.filter { details -> details.areAllApplicable(columnRanges)}.map {it.fieldName})
            options.add(i, possibleFields)
        }

        for (i in 0 until myTicket.size) {
            val toEliminate = options
                .filter {it.size == 1}
                .reduce {acc, current ->
                    acc.union(current)
                }
            println("going to eliminate ${toEliminate}")
            options.forEachIndexed {index, set ->
                if (set.size > 1) {
                    options[index] = set.minus(toEliminate)
                }
            }

        }
        var product = 1L
        options.forEachIndexed {index, set ->
            if (set.first().startsWith("departure")) {
//                println("-${set.first()} -> ${myTicket[index]}")
                product *= myTicket[index]
            }
        }
        return product
    }

    private fun getTicketRows(position: Int): Sequence<List<Int>> =
        noteGroups[position]
            .asSequence()
            .drop(1)
            // flatmap should work here but Kotlin and I aren't getting along
            .map { it.split(",").map { it.toInt() }}

    companion object {
        @JvmStatic
        private val rowMatcher = "(\\w|.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()

        private fun extractRowDetails(line: String): RowDetails? {
            val match = rowMatcher.matchEntire(line)
            return match?.let {
                return RowDetails(match.groupValues[1],
                    match.groupValues[2].toInt()..match.groupValues[3].toInt(),
                    match.groupValues[4].toInt()..match.groupValues[5].toInt(),
                )
            }
        }
    }

    private data class RowDetails(val fieldName: String, val firstRange: IntRange, val secondRange: IntRange) {
        fun isApplicable(potential: Int): Boolean = potential in firstRange || potential in secondRange
        fun areAllApplicable(fields: List<Int>): Boolean =
            fields
                .filter { isApplicable(it) }
                .count() == fields.size

    }

}
