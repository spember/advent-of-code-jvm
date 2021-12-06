package pember.aoc2021

import pember.utils.YearlyFileReader
import kotlin.math.max

class HydrothermalVenture(private val fileName: String) {

    fun findBasicOverLaps(): Int = findOverlaps { it.isVertical() || it.isHorizontal() }

    fun findOverLapsWithDiagonal(): Int = findOverlaps()

    private fun findOverlaps(prFilterer: (PointRange)-> Boolean = {_ -> true}): Int {
        val points = parse()
        return points
            .filter(prFilterer)
            .fold(OceanFloor(findMax(points))) {floor, point -> floor.recordThermals(point) }
            .countOverlaps()
    }

    private fun findMax(points: Sequence<PointRange>): Pair<Int, Int> {
        val fn: (Array<Int>, Pair<Int, Int>) -> Unit = {context, pair ->
            if(pair.first > context[0]) {
                context[0] = pair.first
            }
            if (pair.second > context[1]) {
                context[1] = pair.second
            }
        }
        val arr = points.fold(Array(2) {0}) {context, point ->
            fn(context, point.begin)
            fn(context, point.end)
            context
        }
        return arr[0]+1 to arr[1]+1
    }



    private fun parse(): Sequence<PointRange> =
        YearlyFileReader(2021).readNonEmptyLines(fileName)
            .map { PointRange(it) }

    private class PointRange(private val input: String) {
        var begin: Pair<Int, Int>
        var end: Pair<Int, Int>

        init {
            val bits = input.split("->")
            begin = convertToPair(bits.first())
            end = convertToPair(bits.last())
        }

        fun isHorizontal(): Boolean = begin.second == end.second
        fun isVertical(): Boolean = begin.first == end.first

        fun pointSpan(): List<Pair<Int, Int>>  {
            val xrange = buildRange(end.first - begin.first).toList()
            val yrange = buildRange(end.second - begin.second).toList()
            val nmax = max(xrange.size, yrange.size)
            // it's possible xrange or yrange would be empty (e.g. straight up and down
            return (0 until nmax).map {
                Pair(
                    begin.first+xrange.getOrElse(it) { 0 },
                    begin.second+yrange.getOrElse(it) { 0 }
                )
            }
        }

        private fun buildRange(end: Int): IntProgression {
            // because the range is different on negative
            return if (end < 0) {
                0 downTo end
            } else {
                0.rangeTo(end)
            }
        }

        private fun convertToPair(bit: String): Pair<Int, Int> =
            bit.split(",")
                .map { it.trim().toInt() }
                .take(2) // not strictly necessary
                .zipWithNext { a, b ->  Pair(a,b)}
                .single()

        override fun toString(): String {
            return "${begin} -> ${end}"
        }
    }

    private class OceanFloor(private val max: Pair<Int, Int>) {
        private val spaces: Array<Array<Int>> = Array(max.first) {Array(max.second) {0} }

        fun recordThermals(pointRange: PointRange): OceanFloor {
            pointRange.pointSpan().forEach { (x, y) -> spaces[x][y] += 1 }
            return this
        }

        fun countOverlaps(): Int {
            return spaces.flatMap { it.toList() }.count { it > 1 }
        }
    }
}