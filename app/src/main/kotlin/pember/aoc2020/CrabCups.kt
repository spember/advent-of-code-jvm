package pember.aoc2020

import pember.utils.CircularList

/**
 * Day 23
 */
object CrabCups {


    fun play(initString: String, rounds: Int): String {
        val initialCups = initString.toCharArray().map {it.toString().toInt()}
        val circle = CircularIntList(initialCups)
        runGame(circle, initialCups.first(), rounds)
        return circle.getFrom(1).drop(1).joinToString("")
    }

    fun playInsane(initString: String, rounds: Int): Long {
        val initialCups = initString.toCharArray().map {it.toString().toInt()}
        val circle = CircularIntList(initialCups)
        var previous = initialCups.last()
        for (i in initialCups.maxOrNull()!!+1..1000000) {
            circle.insertAfter(i, previous)
            previous = i
        }

        runGame(circle, initialCups.first(), rounds)
        val cups = circle.removeAfter(1, 2)
        return cups[0].toLong() * cups[1]
    }

    private fun runGame(circle: CircularIntList, startingLabel: Int, rounds:Int) {
        var currentLabel = startingLabel
        for (i in 1..rounds) {
//            println ("Move ${i}")
//            println ("cups = ${circle.getFrom(initialCups.first())}, selected is ${currentLabel}")
            val picked = circle.removeAfter(currentLabel, 3)
//            println("picked: ${picked}")
            val destinationCup = circle.findPrevious(currentLabel)
//            println("designation cup = ${destinationCup}")
            circle.insertAfter(picked, destinationCup)
            currentLabel = circle.getAfter(currentLabel)!!
//            println("\n")
        }
    }
}

class CircularIntList(initial: List<Int>): CircularList<Int>(initial) {

    fun findPrevious(target: Int): Int {
        for (i in target-1 downTo 0) {
            if (index.containsKey(i)) {
                return index[i]!!.value
            }
        }
        // otherwise, max of the cache
        return index.keys.maxOrNull()!!
    }

}
