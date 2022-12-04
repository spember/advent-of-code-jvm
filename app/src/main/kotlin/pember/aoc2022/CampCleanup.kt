package pember.aoc2022

class CampCleanup(private val fileName: String): Aoc2022() {

    fun countEnclosingPairs(): Int = parse()
            .filter {
                // find those where the intersect is the entirety of one of the sets
                val intersecting = it.first.intersect(it.second)
                it.first == intersecting || it.second == intersecting
            }
            .count()

    fun countOverlappingPairs(): Int = parse()
        .map { it.first.intersect(it.second) }
        // find out where there's intersection at all
        .filter { it.isNotEmpty() }
        .count()


    private fun parse(): Sequence<Pair<Set<Int>, Set<Int>>> = reader
        .readNonEmptyLines(fileName)
        .map {it.trim().split(",")}
        .map { convertRange(it.first()) to convertRange(it.last()) }

    companion object {
        private fun convertRange(target: String): Set<Int> {
            val endpoints = target.split("-")
            return (endpoints.first().toInt()..endpoints.last().toInt()).toSet()
        }
    }
}