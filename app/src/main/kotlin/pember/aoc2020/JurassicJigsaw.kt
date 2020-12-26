package pember.aoc2020

import pember.utils.FileReader
import java.lang.StringBuilder
import java.time.Instant

/**
 *
 */
class JurassicJigsaw(file: String) {

    val unsortedTiles = mutableMapOf<Int, Tile>()
    val matches = mutableMapOf<Int, Int>()

    init {
        FileReader.readLinesIntoGroups(file)
            .filter {it.size == 11}
            .map { Tile(it) }
            .forEach { unsortedTiles[it.id] = it }

        println("Received ${unsortedTiles.keys.size} tiles")
//        unsortedTiles.forEach { t, u ->
//            println(u)
//        }
    }

    fun scan(): Long {
        val start = Instant.now().toEpochMilli()
        for (key in unsortedTiles.keys){
            if (!matches.containsKey(key)) {
                matches[key] = 0
            }
            unsortedTiles.forEach { id, tile ->

                if (id != key) {
                    val count = unsortedTiles[key]!!.compareOtherTile(tile)
                    matches[key] = matches[key]!! + count
                }
            }
        }
        val stop = Instant.now().toEpochMilli()
        println("scan complete in ${stop - start}ms")
        unsortedTiles.forEach {id, tile ->
            println("${tile.id} matched with ${tile.matchedWith}")
        }

        println(matches)
        var result = 1L
        matches.forEach {id, count ->
            if (count == 2) {
                result *= id
            }
        }
        return result
    }




    class Tile(inputLines: List<String>) {
        var id: Int = 0
            private set
        var pixels: List<CharArray>
            private set

        val matchedWith = mutableListOf<Int>()

        init {
            // first line is the tile id
            inputLines.take(1).map {
                idMatcher.matchEntire(it)?.let {
                    id = it.groupValues[1].toInt()
                }
            }
            pixels = inputLines.drop(1).filter { it.isNotEmpty() }
                .map {
                    it.toCharArray()
                }
                .toList()
        }

        fun flipTopToBottom() {
            pixels = pixels.reversed()
        }

        fun flipLeftToRight() {
            pixels = pixels.map {
                it.reversed().toCharArray()
            }
        }

        fun compareOtherTile(other:Tile): Int {
            // compare each border of my tile with each other border of the other tile
            // so 16 checks
            // count the number of 'matches'
            // 4 -> center, 3 -> edge, 2-> corner
            val checks: List<Pair<CharArray, CharArray>> = listOf(
                funGenerateBorders(getUpperBorder(), other),
                funGenerateBorders(getLowerBorder(), other),
                funGenerateBorders(getLeftBorder(), other),
                funGenerateBorders(getRightBorder(), other))
                .flatten()
                .filter {
                    // reversed -> flipped
                    bordersMatch(it.first, it.second) || bordersMatch(it.first, it.second.reversed().toCharArray())
                }
            if (checks.size > 0) {
                matchedWith.add(other.id)
            }
            return checks.size
        }

        private fun funGenerateBorders(thisSide: CharArray, other:Tile): List<Pair<CharArray,CharArray>> =
            listOf(thisSide to other.getLeftBorder(),
            thisSide to other.getRightBorder(),
            thisSide to other.getUpperBorder(),
            thisSide to other.getLowerBorder())

        fun getUpperBorder(): CharArray = pixels[0]
        fun getLowerBorder(): CharArray = pixels[9]
        fun getLeftBorder(): CharArray = pixels.map { it[0] }.toCharArray()
        fun getRightBorder(): CharArray = pixels.map {it[9] }.toCharArray()



        override fun toString(): String {
            val builder = StringBuilder("Title: $id\n")
            pixels.forEach {
                builder.append(it.joinToString(""))
                builder.append("\n")
            }
            return builder.toString()
        }

        companion object {
            private val idMatcher = "Tile (\\d+):".toRegex()

            fun bordersMatch(a: CharArray, b: CharArray): Boolean {
                var matches = true
                for (i in a.indices) {
                    if (a[i] != b[i]) {
                        matches = false
                        break
                    }
                }
                return matches
            }
        }
    }
}
