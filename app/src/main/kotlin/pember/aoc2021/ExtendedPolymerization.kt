package pember.aoc2021

import pember.utils.AocPuzzle21

/**
 * I needed a hint on this one today. I wrote a solution to the first puzzle in 20 minutes, but of course puzzle 2
 * was not so trivial. The hint needed was to track counts of he generated pairs of the symbols, rather than trying to build
 * up the full string
 */
class ExtendedPolymerization(private val fileName: String): AocPuzzle21(fileName) {

    fun polymerElements(steps: Int): Long {
        val (initialTemplate, elements) = parse()

        val final = (1..steps).fold(buildPolymer(initialTemplate)) {polymer:PolymerPairs, _ ->
            realStep(polymer, elements)
        }
        // these pairs! consider just the first letter in the pair for it's count
        val stats: MutableMap<Char, Long> = mutableMapOf()
        final.forEach {(c, _), count ->
            stats[c] = stats.getOrDefault(c, 0L) + count as Long
        }
        return stats.maxByOrNull { it.value }!!.value - stats.minByOrNull { it.value }!!.value
    }

    private fun buildPolymer(template: String): PolymerPairs {
        val pairs = template.windowed(2, 1, false).map {Pair(it[0], it[1])}.toMutableList()
        pairs.add(Pair(template.last(), '_'))
        val polymer = mutableMapOf<Pair<Char, Char>, Long>()
        pairs.forEach{ polymer[it] = polymer.getOrDefault(it, 0L) + 1}
        return polymer
    }


    private fun realStep(polymer: PolymerPairs, rules: Map<Pair<Char, Char>, Char>): PolymerPairs {

        val updatedPolymer: PolymerPairs = mutableMapOf()

        for ((key, pairCounts) in polymer) {
            if (rules.containsKey(key)) {
                val (l, r) = key
                val n = rules[key]!!
                updatedPolymer[Pair(l, n)] = updatedPolymer.getOrDefault(Pair(l, n), 0L) + pairCounts
                updatedPolymer[Pair(n, r)] = updatedPolymer.getOrDefault(Pair(n, r), 0L) + pairCounts
            } else {
                updatedPolymer[key] = pairCounts // likely just the single end
            }
        }
        return updatedPolymer
    }

    private fun parse(): Pair<String, Map<Pair<Char, Char>, Char>> {
        val raw = reader().readLinesIntoGroups(fileName).toList()
        // raw[0] is the first half of the pair
        val elements = raw.last().map { it.split(" -> ") }.associate { Pair(Pair(it[0][0], it[0][1]), it[1][0]) }
        return Pair(raw[0][0], elements)
    }


}

typealias PolymerPairs = MutableMap<Pair<Char, Char>, Long>