package pember.aoc2022

import java.lang.Error
import kotlin.math.abs

class RopeBridge(private val fileName: String): Aoc2022() {
    private val instructions: Sequence<Pair<String, Int>> =
        reader.readNonEmptyLines(fileName)
        .map { it.split(" ") }
        .map { it.first().toUpperCase() to it.last().toInt()}

    private val board = Array(32) { IntArray(32) {0} }

    fun countTailPositions(knotCount: Int = 2): Int {
        var knots = mutableListOf<Knot>()
        for (i in 0 until knotCount) {
            knots.add(Knot(0,0))
        }
        val tailPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(0 to 0)

        instructions.forEach {
//            println("Moving ${Directions.lookup(it.first)} -> ${it.second}")
            // for each step in the magnitude, need to check the TAIL to catch up
            // apply each direction for the magnitude
            for (s in 0 until it.second) {
                when(Directions.lookup(it.first)) {
                    // y, x
                    Directions.UP -> knots.first().col++
                    Directions.RIGHT -> knots.first().row++
                    Directions.LEFT -> knots.first().row--
                    Directions.DOWN -> knots.first().col--
                }
                // tail moves if head is more than one away
                for (k in 1 until knots.size) {
                    moveKnot(knots[k-1], knots[k])
                }
                val current = knots.last().col to knots.last().row
                tailPositions.add(current)
            }
        }
        return tailPositions.size
    }

    private fun moveKnot(source: Knot, follower: Knot) {
        if (abs(source.col-follower.col) > 1) {
            if (source.col > follower.col) {
                follower.col++
            } else {
                follower.col--
            }
            follower.row = source.row
        } else if (abs(source.row - follower.row) > 1) {
            if (source.row > follower.row) {
                follower.row++
            } else {
                follower.row--
            }
            follower.col = source.col
        }
    }

    private data class Knot(var col: Int, var row: Int)


    private enum class Directions(val raw: String) {
        RIGHT("R"), UP("U"), LEFT("L"), DOWN("D");

        companion object {
            fun lookup(raw:String): Directions {
                for( dir in Directions.values()) {
                    if (dir.raw == raw) {
                        return dir
                    }
                }
                throw Error()
            }
        }

    }
}