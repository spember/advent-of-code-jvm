package pember.aoc2022

import java.util.LinkedList

class HillClimbing(private val fileName: String): Aoc2022() {

    private lateinit var target: Pair<Int, Int>
    private lateinit var currentPosition: Pair<Int, Int>
    private val availablePaths: MutableMap<Pair<Int, Int>, List<Pair<Int, Int>>> = mutableMapOf()

    private val map = reader.readLines(fileName)
        .map { it.trim() }
        .mapIndexed {rowPos, row ->
            row.mapIndexed {colPos, char ->
                when(char) {
                    'S' -> {
                        currentPosition = rowPos to colPos
                        Square('a', 'a'.toInt())
                    }
                    'E' -> {
                        target = rowPos to colPos
                        Square('z', 'z'.toInt())
                    }
                    else -> Square(char, char.toInt())
                }
            }
        }.toList()

    init {
        for (r in map.indices) {
            for (c in map[r].indices) {
                availablePaths[r to c] = findAvailableSteps(r to c)
            }
        }
        map.forEach {
            println(it.map { s -> s.toString() }.joinToString(", "))
        }
    }


    fun findShortestSteps(): Int {
        return findLeastStepsFrom(currentPosition)
    }

    private fun findLeastStepsFrom(start: Pair<Int, Int>): Int {
        val reachedFrom: MutableMap<Pair<Int, Int>, Pair<Int, Int>> = mutableMapOf()
        val nodesToCheck = LinkedList<Pair<Int, Int>>()
        nodesToCheck.add(start)
        while(!nodesToCheck.isEmpty()) {
            val current = nodesToCheck.poll()
            availablePaths[current]!!.forEach {

                if (!reachedFrom.contains(it)) {
                    reachedFrom[it] = current
                    nodesToCheck.add(it)
                }

            }
        }

        val path = mutableListOf(target)
        try {
            while (path.last() != start) {
                path.add(reachedFrom[path.last()]!!)
            }
            return path.size - 1
        } catch (npe:NullPointerException ) {
            return Int.MAX_VALUE
        }

    }

    fun fromAllAs(): Int {
        // find all a's
        val startAs = mutableListOf<Pair<Int, Int>>()
        for (r in map.indices) {
            for (c in map[r].indices) {
                if (map[r][c].height == 'a') {
                    startAs.add(r to c)
                }

            }
        }
        return startAs.map { findLeastStepsFrom(it) }.minOf { it }
    }


    private fun findAvailableSteps(position: Pair<Int, Int>): List<Pair<Int, Int>> {
        return mutableListOf(
            position.first-1 to position.second,
            position.first+1 to position.second,
            position.first to position.second + 1,
            position.first to position.second - 1
        )
            // boundary
            .filter { it.first >= 0 && it.first < map.size }
            .filter { it.second >= 0 && it.second < map.first().size }
            // test height
            .filter {
                // it value must be <= position.value+1
                map[it.first][it.second].value <= (map[position.first][position.second].value + 1)
            }
    }

    private data class Square(val height: Char, val value: Int) {
        override fun toString(): String {
            return "$height ($value)"
        }
    }
}