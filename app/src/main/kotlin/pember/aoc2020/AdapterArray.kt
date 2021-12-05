package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 9
 */
object AdapterArray {

    fun findSteps(fileName: String): Int {
        println("reading lines?")
        return FileReader
            .readLinesAsInts(fileName)
            .toList()
            .sorted()
            .fold(ScanningContext()) {context, joltage -> context.plugInAdapter(joltage)}
            .plugIntoSocket()
            .calculateDifferences()
    }

    fun findAllPossibleSteps(fileName: String): Long {
        val inputs= FileReader.readLinesAsInts(fileName)
            .toList()
            .sorted()
        println("Data is ${inputs}")
        val result = scanGaps(inputs)
        return result

    }

    private fun scanGaps(inputsMissing: List<Int>): Long {
        // DP solution!
        var inputs = inputsMissing + listOf(inputsMissing.last()+3)
        var dpCache = Array(inputs.last()+1) {0L}
        dpCache[0] = 1 // 1 way from the adapter

//        println(dpCache.toList().toString())
        for (i in 1 until dpCache.size) {
            if (i in inputs) {
                dpCache[i] += dpCache[i-1]
                if (i >= 2 && dpCache[i-2] > 0) {
                    dpCache[i] += dpCache[i-2]
                }
                if (i >= 3 && dpCache[i-3] > 0) {
                    dpCache[i] += dpCache[i-3]
                }
            }
        }
        println("cache is now ${dpCache.toList()}")
        println("last val is ${dpCache[dpCache.size-3]}, ${dpCache.last()}")
        return dpCache.last()
    }


    private class ScanningContext() {
        // use an array to count the number of 1 (0), 2 (1), and 3(2) step differences
        private var countTracker = Array(3) {0}
        private var previousJolt = 0 // 0 for the initial port

        fun plugInAdapter(adapterJoltage: Int): ScanningContext {
            val diff = (adapterJoltage-previousJolt)-1
            if (diff < countTracker.size) {
                countTracker[diff] += 1
            }
            previousJolt = adapterJoltage

            return this
        }

        fun plugIntoSocket(): ScanningContext {
            // after all adapters are plugged together.. plug into the wall
            // one more 3-step joltage difference
            countTracker[2] += 1
            return this
        }

        /**
         * Returns a calculation based on 1 and 3 jolt differences because of Reasons
         */
        fun calculateDifferences(): Int {
            println("Tracker is ${countTracker.toList()}")
            return countTracker[0] * countTracker[2]
        }

        override fun toString(): String {
            return countTracker.toList().toString()
        }

    }
}
