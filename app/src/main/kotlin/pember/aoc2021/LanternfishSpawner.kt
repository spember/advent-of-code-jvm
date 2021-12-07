package pember.aoc2021

import pember.utils.YearlyFileReader

class LanternfishSpawner(private val fileName: String) {

    // part 2 follow up... don't track individual fish, but instead the total per day!

    // each day, take the count and move it the previous position
    // at the end, take the amount in zero position and set that at position 8. Add that number fo position 6

    fun simulate(days: Int): Long {
        val fishDays: Array<Long> = Array(9) {0}

        YearlyFileReader(2021)
            .readLines(fileName)
            .first()
            .split(",")
            .map { it.toInt() }
            .forEach { fishDays[it] = fishDays[it] + 1}

        (0 until days).forEach { _ ->
            singleRun(fishDays)
        }
        return fishDays.sum()
    }

    private fun singleRun(fishDays: Array<Long>){
        val toSpawn = fishDays.first()
        for (i in 0..fishDays.size-2) {
            fishDays[i] = fishDays[i+1]
        }
        fishDays[fishDays.size-1] = toSpawn
        fishDays[6] += toSpawn
    }

    // in my first attempt I tried to model each fish individually. Part 2 caused me to rework the entire thing
    // and track fish by days

//
//    class Lanternfish(private var daysToSpawn: Int) {
//
//        fun tick():Lanternfish? {
//            daysToSpawn--
//            return if (daysToSpawn < 0) {
//                daysToSpawn = 6
//                Lanternfish(8)
//            } else {
//                null
//            }
//
//        }
//
//        fun daysRemaining(): Int = daysToSpawn
//
//        override fun toString(): String {
//            return "F:${daysToSpawn}"
//        }
//
//    }
}