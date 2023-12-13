package pember.aoc2023

import java.lang.RuntimeException
import java.util.LinkedList

object PipeMaze: Aoc2023(10) {

    fun part1() {
        val easy = findFarthest("day$day-easy.txt")
        println("Easy -> $easy should be 4")
        val test = findFarthest("day$day-test.txt")
        println("Test -> $test should be 8")

        val p1 = findFarthest("day$day.txt")
        println("P1 -> $p1")
    }

    fun part2() {
        val easy = findFarthest("day$day-easy.txt")
        println("Easy -> ${easy.second} should be 1")
        val test2 = findFarthest("day$day-test2.txt")
        println("test2 - > ${test2.second} . should be 4")
        val test3 = findFarthest("day$day-test3.txt")
        println("test3 - > ${test3.second} . should be 8")

        val test4 = findFarthest("day$day-test4.txt")
        println("test4 - > ${test4.second} . should be 10")
        val p2 = findFarthest("day$day.txt")
        println("p2 - > ${p2.second} . should be 355")
    }

    private fun findFarthest(fn: String): Pair<Int, Int> {
        val grid = reader.readLinesIntoGrid(fn) {c, pair -> Pipe(c, pair)  }
        val sPos  = findStartLocation(grid)
        grid[sPos.first][sPos.second].visited= true

        // figure out what S actually is.
        val starts = findStartingNodes(grid, sPos)
        println("Starting position is ${sPos} and first moves are to $starts")
        // now, add to queue. keep traveling to the next position, Track the count + nextStep
        val travel = LinkedList<TravelStep>()
        val pipeNodes: MutableSet<Pair<Int, Int>> = mutableSetOf(sPos)
        starts.forEach {
            travel.add(it to 1)
            pipeNodes.add(it)
        }

        var maxStepsSeen = 0

        while(travel.isNotEmpty()) {
            val currentSpot = travel.poll()
            val pipe = grid[currentSpot.first.first][currentSpot.first.second]
            if (pipe.visited) {
                continue
            }
            pipe.visited = true
            pipeNodes.add(pipe.loc)
            if (currentSpot.second > maxStepsSeen) {
                maxStepsSeen = currentSpot.second
            }
            val stepCount = currentSpot.second+1

            val options = grid[currentSpot.first.first][currentSpot.first.second].findConnections() ?: continue
            if(!grid[options.first.first][options.first.second].visited) {
                travel.add(options.first to stepCount)
            }
            if (!grid[options.second.first][options.second.second].visited) {
                travel.add(options.second to stepCount)
            }
        }
        println("Total Pipe Nodes ${pipeNodes.size}. finding enclosed spaces")
        findContainedSpaces(grid, pipeNodes)
        return maxStepsSeen to findContainedSpaces(grid, pipeNodes)
    }

    private fun findContainedSpaces(grid: Grid, nodes: Set<Pair<Int, Int>>): Int {
        // scan from left to right. if moving onto an enclosed area.
        // - pipes
        var emptyCount = 0
        var insideLoop = false
        val inPipes = listOf('|', 'L', 'J')
        val skipPipes = listOf('|', '7', 'F', 'S')

        for (y in grid.indices) {
            for (x in grid[y].indices) {
                val pos = grid[y][x]
                if (inPipes.contains(pos.symbol) && nodes.contains(pos.loc ) ){
                    if (!insideLoop) {
                        insideLoop = true
//                        println("Moving in at ${pos.loc}")
                    } else {
//                        println("Moving out at ${pos.loc}")
                        insideLoop = false
                    }
                }
                if (!nodes.contains(pos.loc) && insideLoop) {
//                    println("Counting ${pos.loc}")
                    emptyCount++
                }
            }
        }
//        println("inside = ${emptyCount}")
        return emptyCount
    }

    private fun findStartLocation(grid: Grid): Pair<Int, Int> {
        for(y in grid.indices) {
            for(x in grid[y].indices) {
                if (grid[y][x].symbol == 'S') {
                    return y to x
                }
            }
        }
        throw RuntimeException("Could not find the Start")
    }

    private fun findStartingNodes(grid: Grid, pos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val validSpaces = listOf(
            pos.first -1 to pos.second,
            pos.first+1 to pos.second,
            pos.first to pos.second-1,
            pos.first to pos.second+1
        )
            .filter { it.first >=0 && it.first < grid.size }
            .filter { it.second >=0 && it.second < grid[0].size }

        val starts = mutableListOf<Pair<Int, Int>>()
        validSpaces.forEach {
            val conns = grid[it.first][it.second].findConnections()
            if (conns != null && (conns.first == pos|| conns.second == pos)) {
                starts.add(it)
            }
        }
        return starts
    }


    class Pipe(val symbol: Char, val loc: Pair<Int, Int>, var visited: Boolean = false) {

        /**
         * Find locations of pipes that can connect INTO ME.
         * Useful for figuring out what S is
         */
        fun findConnections(): Connections? {
            // depending on my symbol, send a pair of coordinates back
            return when(symbol) {
                '.' -> null
                '|' -> (loc.first+1 to loc.second) to (loc.first-1 to loc.second)
                '-' -> (loc.first to loc.second-1) to (loc.first to loc.second+1)
                'L' -> (loc.first-1 to loc.second) to (loc.first to loc.second+1)
                'J' -> (loc.first-1 to loc.second) to (loc.first to loc.second-1)
                '7' -> (loc.first+1 to loc.second) to (loc.first to loc.second-1)
                'F' -> (loc.first+1 to loc.second) to (loc.first to loc.second+1)

                else -> throw RuntimeException("unknown symbol $symbol")
            }
        }

        override fun toString(): String {
            return symbol.toString()
        }
    }

}

typealias Grid = Array<Array<PipeMaze.Pipe>>
typealias Connections = Pair<Pair<Int, Int>, Pair<Int, Int>>
typealias TravelStep = Pair<Pair<Int, Int>, Int>

/*
| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
 */