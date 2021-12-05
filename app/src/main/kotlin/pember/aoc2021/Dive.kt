package pember.aoc2021

import pember.utils.YearlyFileReader

object Dive {

    fun basicDive(fileName: String): Int =
            parse(fileName)
            .fold(DepthTracker()) { tracker, data -> tracker.move(data.first, data.second) }
            .calculateReward()

    fun aim(fileName: String): Int =
        parse(fileName)
            .fold(DepthTracker()) {tracker, data -> tracker.aim(data.first, data.second)}
            .calculateReward()


    private fun parse(fileName: String): Sequence<Pair<String, Int>> =
        YearlyFileReader(2021).readNonEmptyLines(fileName)
            .map { it.split(" ") }
            .map { (first, second) -> Pair(first, second.toInt())}
}

class DepthTracker() {
    var horizontal = 0
    var depth = 0
    var aim = 0


    fun move(direction: String, amount: Int): DepthTracker {
        when(direction){
            D -> depth += amount
            U -> depth -= amount
            F -> horizontal += amount
            else -> {
                println("I do not know direction $direction")
            }
        }
        return this
    }

    fun aim(direction: String, amount: Int): DepthTracker {
        when(direction){
            D -> aim += amount
            U -> aim -= amount
            F -> {
                horizontal += amount
                depth += aim * amount
            }
            else -> {
                println("I do not know direction $direction")
            }
        }
        return this
    }

    fun calculateReward(): Int {
        println("Calculating reward based on $horizontal, $depth")
        return horizontal * depth

    }

    companion object {
        private const val F: String = "forward"
        private const val D: String = "down"
        private const val U: String = "up"
    }
}