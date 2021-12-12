package pember.aoc2021

import pember.utils.AocPuzzle21

class PassagePathing(filename: String): AocPuzzle21(filename) {

    fun countPaths(allowOneSmallCave: Boolean=false): Int {
        val segments = mutableMapOf<Cave, MutableList<Cave>>()
        readLines().map { it.split("-") }
            .map { Pair(Cave(it[0]), Cave(it[1]))}
            .forEach { (c1, c2) ->
                if (!segments.containsKey(c1)) {
                    segments[c1] = mutableListOf()
                }
                if (!segments.containsKey(c2)) {
                    segments[c2] = mutableListOf()
                }
                segments[c1]!!.add(c2)
                segments[c2]!!.add(c1)
            }

        val results = explore(segments, mutableSetOf(), mutableListOf(Cave("start")), allowOneSmallCave)
        println("found ${results.size} total paths")
        return results.size
    }

    /**
     * returns completed path
     */
    private fun explore(
        segments: Map<Cave, List<Cave>>,
        foundPaths: MutableSet<List<Cave>>,
        currentPath: List<Cave>,
        allowOneSmallCave:Boolean = false
    ): Set<List<Cave>> {
        val currentCave = currentPath.last()
        if (currentCave.isEnd) {
            foundPaths.add(currentPath.toList())
        } else {
            // find next segments
            for (nextCave in segments[currentCave]!!) {
                // can I go there? only if it's big or it's small and it's not in current path
                if (mayExplore(nextCave, currentPath, allowOneSmallCave)) {
                    explore(segments, foundPaths, currentPath + nextCave, allowOneSmallCave)
                }
            }
        }
        return foundPaths
    }

    private fun mayExplore(nextCave: Cave, currentPath: List<Cave>, allowOneSmallCave: Boolean): Boolean {
        // this is gross. but it's sunday morning and I don't want to think about this more
        return if (nextCave.isBig) {
            true
        } else if (allowOneSmallCave) {
            if (nextCave.name == START) {
                return false
            }
            !currentPath.contains(nextCave) || currentPath.filter {!it.isBig}.groupingBy { it }.eachCount().all { it.value == 1 }
        } else {
            !currentPath.contains(nextCave)
        }
    }


    data class Cave(val name: String) {
        val isBig: Boolean = name.all {it.isUpperCase()}

        val isEnd: Boolean = name == END

        override fun toString(): String {
            return "${name}(${isBig})"
        }
    }

    companion object {
        private const val START = "start"
        private const val END = "end"
    }
}