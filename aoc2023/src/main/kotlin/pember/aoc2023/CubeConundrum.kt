package pember.aoc2023

import java.lang.Error

object CubeConundrum: Aoc2023() {

    fun countPossible(fn: String): Int {
        //12 red cubes, 13 green cubes, and 14 blue cubes
        val phase1Pool = mapOf<Colors, Int>(
            Colors.RED to 12,
            Colors.GREEN to 13,
            Colors.BLUE to 14
        )
        return reader.readLines(fn).map { extract(it) }
        .filter { it.isPossible(phase1Pool) }
        .sumBy{ it.id}
    }

    fun findMinimum(fn: String): Int {
        return reader.readLines(fn).map { extract(it) }
            .map { it.calculateMinimum() }
            .sum()
    }

    private fun extract(line: String): Game {
        val pieces = line.split(":")
        val game: Game = Game(Integer.parseInt(pieces[0].removePrefix("Game ")))
        pieces[1].split(";").forEach{game.reveal(it)}
        return game
    }

    private class Game(val id: Int) {
        val reveals = mutableListOf<Map<Colors, Int>>()

        fun reveal(handful: String) {
            val play = handful.split(",")
                .map { it.trim().split(" ") }
                .map {Integer.parseInt(it[0]) to Colors.lookup(it[1]) }
                .fold(mutableMapOf<Colors, Int>()) { data, pair ->
                    data[pair.second] = pair.first
                    data
                }
            reveals.add(play)
        }

        fun isPossible(pool: Map<Colors, Int>): Boolean {
            // if any value in a reveal is greater than the value in the pool -> impossible
            reveals.forEach {r ->
                for (k in pool.keys) {
                    if (r.contains(k) && r[k]!! > pool[k]!!) {
//                        println("Game $id is impossible! ${k}")
                        return false
                    }
                }
            }
            return true
        }

        // may need to check for 0
        fun calculateMinimum(): Int = findMaxOf(Colors.RED) * findMaxOf(Colors.BLUE) * findMaxOf(Colors.GREEN)

        private fun findMaxOf(color: Colors): Int = reveals.map { it.getOrDefault(color, 1) }.maxOrNull()!!

     }

    private enum class Colors(val value: String) {
        BLUE("blue"),
        RED("red"),
        GREEN("green");
        companion object {
            fun lookup(raw: String): Colors {
                for(c in Colors.values()) {
                    if (c.value == raw) {
                        return c
                    }
                }
                throw Error("no color")
            }
        }
    }
}