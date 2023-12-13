package pember.aoc2023

import java.lang.Integer.min
import kotlin.math.max

object CosmicExpansion: Aoc2023(11) {

    fun part1() {
        val test = countSteps(fn("-test"), )
        println("p1 test -> ${test} should be 374")

        val p1 = countSteps(fn(""), )
        println("p1 -> ${p1} should be 9609130")
    }

    fun part2() {
        val test10 = countSteps(fn("-test"), 10)
        println("p2 x 10 test -> ${test10} should be 1030")

        val test100 = countSteps(fn("-test"), 100)
        println("p2 x 100 test -> ${test100} should be 8410")

        val p2 = countSteps(fn(""), 1000000)
        println("p2  -> ${p2} should be 702152204842")
    }

    private fun countSteps(fn: String, expansionMult: Int=2): Long {
        val grid = reader.readLinesIntoCharGrid(fn)

        val northSouth = findEmptyRows(grid)
        val eastWest = findEmptyColumns(grid)

        val galaxies = findGalaxies(grid)
        println("Found ${galaxies.size} galaxies")
        val galaxyPairs: List<GalaxyPair> = createPairs(galaxies)
        println("Pairs are ${galaxyPairs.size}:")
        return galaxyPairs.map {
            val steps = walkShortestPath(it, grid, northSouth, eastWest, expansionMult)
//            println("-> $it -> ${steps}")
            steps
        }
            .sum()
    }


    private fun walkShortestPath(pair: GalaxyPair, grid: Array<CharArray>, northSouth: List<Int>, eastWest: List<Int>, expansion: Int) : Long {
//        println("Walking ${pair.first} to ${pair.second}")
//        println(abs(pair.second.second-pair.first.second) + abs(pair.second.first-pair.first.first))
        // max of value down to min if either north south or east west is in there, add 1


        return calcSteps(pair.first.second, pair.second.second, eastWest, expansion) +
                calcSteps(pair.first.first, pair.second.first, northSouth, expansion)
    }

    private fun calcSteps(a: Int, b: Int, growths: List<Int>, expansion:Int=2): Long {
        // 2 == double!
        var steps = 0L;
        for (i in min(a,b) until max(a,b)) {
            if (growths.contains(i)) {
                steps += (1*expansion)
            } else {
                steps++
            }
        }
        return steps
    }

    private fun findEmptyRows(grid: Array<CharArray>): List<Int> {
        val rowNums = mutableListOf<Int>()
        grid.forEachIndexed {i, it ->
            if (it.all {point -> point == EMPTY}) {
                rowNums.add(i)
            }
        }
        return rowNums
    }

    private fun findEmptyColumns(grid: Array<CharArray>): List<Int> {
        val colNums = mutableListOf<Int>()
        for (x in grid[0].indices) {
            if (sequence<Char> { for (y in grid[x].indices) { yield(grid[y][x]) } }.all {c -> c == EMPTY}) {
                colNums.add(x)
            }
        }
        return colNums
    }

    private fun findGalaxies(grid: Array<CharArray>): List<Pair<Int, Int>> =
        sequence<Pair<Int, Int>> {
            for (y in grid.indices) {
                for (x in grid[y].indices) {
                    yield(y to x)
                }
            }
        }
            .filter { grid[it.first][it.second] == GALAXY }
            .toList()

    private fun createPairs(galaxies: List<Pair<Int, Int>>): List<GalaxyPair> {
        val pairs = mutableListOf<GalaxyPair>()
        galaxies.forEachIndexed { t, galaxy ->
            for(x in t until galaxies.size) {
                if (x!=t) {
                    pairs.add(galaxy to galaxies[x])
                }
            }
        }
        // for each in the last, make pairs with the rest
        return pairs
    }


    private const val EMPTY = '.'
    private const val GALAXY = '#'
}

typealias GalaxyPair = Pair<Pair<Int, Int>, Pair<Int, Int>>