package pember.aoc2023

import java.lang.Long.parseLong

object WaitForIt: Aoc2023() {

    fun waysToWin(fn: String): Long {
        val games: List<Race> = reader.readLines(fn).windowed(2, 2, false).map {
            val times = parseLine(it[0])
            val distances = parseLine(it[1])
            times.zip(distances).map { Race(it.first.toLong(), it.second.toLong()) }
        }.flatMap { it }.toList()

        // but also

        return games
            .map { it.countWinners() }
            .fold(1L) { acc: Long, n: Long -> acc * n }
    }

    fun badKerning(fn: String): Long {
        val game: Race = reader.readLines(fn).windowed(2, 2, false).map {
            Race(parseKernedLine(it[0]), parseKernedLine(it[1]))
        }.toList().first()
        println("game = ${game.recordDistance}")

        return game.countWinners()
    }

    private fun parseLine(line: String): List<Int> = line.split(":").last()
        .trim().split(" ")
        .filter { it.isNotEmpty() }
        .map { Integer.parseInt(it) }

    private fun parseKernedLine(line: String): Long = parseLong(line.split(":").last()
        .trim().replace(" ", ""))

    private class Race(val duration: Long, val recordDistance: Long) {

        fun countWinners(): Long {
            // start in the middle
            val mid = duration/2

            var wins = 0L
            var leftPos = mid-1
            while(isWinner(leftPos)) {
                wins++
                leftPos-=1
            }
            var rightPos = mid
            while(isWinner(rightPos)) {
                wins++
                rightPos+=1
            }
            return wins
        }

        private fun isWinner(tick: Long): Boolean {
            return (tick * (duration-tick)) > recordDistance
        }
    }
}