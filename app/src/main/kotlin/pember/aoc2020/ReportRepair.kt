package pember.challenges.aoc.aoc2020

import pember.utils.FileReader

object ReportRepair {
    /**
     * o save your vacation, you need to get all fifty stars by December 25th.

    Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

    Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input); apparently, something isn't quite adding up.

    Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.

    For example, suppose your expense report contained the following:

    1721
    979
    366
    299
    675
    1456
    In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.

    Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you multiply them together?
     */
    fun run(): Int {

        // load input into array
        val data = extract()

        val remainingLookup = mutableMapOf<Int, Int>()

        data.forEach {
            remainingLookup[2020-it] = it
        }

        val foundPair = data.filter { remainingLookup.containsKey(it) }
            .map { foundItem ->
                Pair(foundItem, remainingLookup[foundItem]!!)
            }
            .first()


        println("found numbers ${foundPair.first}, ${foundPair.second} -> ${foundPair.first + foundPair.second} = ${foundPair.first*foundPair.second}")
        return foundPair.first*foundPair.second
    }

    fun run3():Int {
        val data = extract()
        val remainingLookup = mutableMapOf<Int, Int>()

        data.forEach {
            remainingLookup[2020-it] = it
        }
        var found1 = 0
        var found2 = 0
        var found3 = 0

        // n2 loop over data to see if remaining
        for (i in data.indices) {
            for (j in data.indices) {
                if (i == j) {
                    continue
                } else {
                    val combo = data[i] + data[j]
                    if (remainingLookup.containsKey(combo)) {
                        found1 = remainingLookup[combo]!!
                        found2 = data[i]
                        found3 = data[j]
                        println("found numbers ${found1}, ${found2}, ${found3} -> ${found1+found2+found3} = ${found1*found2*found3}")
                    }
                }
            }
        }
        println("final: found numbers ${found1}, ${found2}, ${found3} -> ${found1+found2+found3} = ${found1*found2*found3}")
        return found1*found2*found3
    }

    private fun extract(): List<Int>  =
        FileReader.readLines("reportRepair.txt")
            .filter {it.isNotEmpty()}
            .map { it.toInt() }
            .toList()

}
