package pember.aoc2020

import pember.utils.FileReader

/**
 *
 */
class ShuttleSearch(fileInput: String) {
    val earliestDepartTime: Int
    val busIds: List<String>

    init {
        val lines = FileReader.readLines(fileInput).filter{it.isNotEmpty()}.toList()
        earliestDepartTime = lines[0].toInt()
        busIds = lines[1].split(",")
        println("Earliest time is ${earliestDepartTime}: Ids are ${busIds}")
    }

    fun findEarliestTime():Int {
        val found = busIds
            .asSequence()
            .filter { it != "x" }
            .map {it.toInt()}
            .map {busId ->
                val nextAfterDepatutureTime = busId * (1 + (earliestDepartTime / busId))
                busId to (nextAfterDepatutureTime-earliestDepartTime)
            }
            .sortedBy { it.second }
            .first()
        println("Earliest bus is ${found.first} -> ${found.second}")
        return found.first * found.second
    }

    fun findSubsequentTimes(): Long {
        val busInfo = mutableListOf<Pair<Int, Int>>()
            busIds.forEachIndexed { index, value ->
            if (value != "x") {
                busInfo.add(value.toInt() to index)
            }
        }
        // had to look up something called the Chinese remainder theorem
        var timestamp: Long = 0L
        var increment = busInfo[0].first.toLong()
        for (i in 1 until busInfo.size) {
            var remainder = 1L
            while(remainder != 0L) {
                timestamp += increment
                remainder = (timestamp+busInfo[i].second.toLong()) % busInfo[i].first.toLong()
            }
            println("Found match for ${busInfo[i].first} at ${timestamp}")
            increment *= busInfo[i].first
        }

        return timestamp
    }
}
