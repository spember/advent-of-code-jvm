package pember.aoc2020

import pember.utils.FileReader
import java.util.LinkedList
import java.util.Queue

/**
 * Day 9:
 * https://adventofcode.com/2020/day/9
 */
object EncodingError {
    fun findFirstFailure(fileName: String): Int = findFirstFailure(DEFAULT_PREAMBLE_SIZE, fileName)


    fun findFirstFailure(preambleSize: Int, fileName: String): Int {
        val parts = FileReader.readPartial(preambleSize, fileName)
        val currentPreamble = PreambleQueue(parts.first.map {it.toInt()})

        var firstFailure: Int = 0

        val items = parts.second.toList()
        for (i in items.indices) {
            val target = items[i].toInt()
            if (currentPreamble.check(target)) {
                currentPreamble.addNext(target)
            } else {
                println("$target did not check!")
                firstFailure = target
                break
            }
        }
        return firstFailure
    }


    /**
     * Call this after finding the initial failure number. Will calculate the Int that represents the 'weakness'
     * in the encryption
     */
    fun findEncryptionWeakness(fileName: String, firstFailure: Long): Long {
        val values = FileReader.readLines(fileName).filter { it.isNotEmpty() }.map {it.toLong()}.toList()
        for (startIndex in values.indices) {
            val ranges = subScan(startIndex, values, firstFailure)
            if (ranges.second - ranges.first > 1) {
                val subList = values.subList(ranges.first, ranges.second).sorted()

                return subList.first() + subList.last()
            }
        }
        return 0L
    }

    private fun subScan(startIndex:Int, values: List<Long>, target:Long): Pair<Int, Int> {
        var count = 0L
        for (i in startIndex until values.size) {
            count += values[i]
            if (count == target) {
                println("Found it! ${startIndex}, ${i} ... ${values[startIndex]} + ${values[i]}")
                return startIndex to i
            } else if (count > target) {
                // boo
                return 0 to 0
            }
        }
        return 0 to 0
    }




    private class PreambleQueue(initialPreamble: List<Int>) {
        // wraps the mechanism to insert a new item into the queue, and see if a given value is made up of a pair
        private val preambleSize = initialPreamble.size
        private val currentPreamble: Queue<Int> = LinkedList(initialPreamble)

        fun addNext(nextInSequence: Int) {
            currentPreamble.poll()
            currentPreamble.offer(nextInSequence)
        }

        /**
         * Scan the Queue to see if the [target] is made up of a pair of values within
         */
        fun check(target: Int): Boolean {
            val remainingCache: MutableMap<Int, MutableList<Int>> = mutableMapOf()
            currentPreamble.forEachIndexed { index, value ->
                val remaining = target-value
                if (!remainingCache.containsKey(remaining)) {
                    remainingCache[remaining] = mutableListOf()
                }
                remainingCache[remaining]!!.add(index)
            }

            // now scan the queue again to look if each value is in the cache, but with a different index
            // (so, not itself)
            var canPair = false
            currentPreamble.forEachIndexed { index, value ->
                val validIndices = remainingCache.getOrDefault(value, mutableListOf()).minus(index)
                if (validIndices.isNotEmpty()) {
//                    println("found $value for ${index} in ${validIndices}")
                    canPair = true
                }
            }

            return canPair

        }
    }

    private const val DEFAULT_PREAMBLE_SIZE = 25
}
