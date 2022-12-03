package pember.aoc2022

class RucksackReorganization(private val fileName: String): Aoc2022() {

    fun sumOverlappingPriorities(): Int {
        return read()
                .map {
                    val l = it.length / 2
                    it.substring(0 until l).toCharArray() to it.substring(l, it.length).toCharArray()
                }
                .map {compartments ->
                    compartments.first.intersect(compartments.second.toList().toSet())
                }
                .flatMap { chars -> chars.map { mapToPriority(it) } }
                .sum()
    }

    fun sumBadgePriority(): Int {
        // window the sequence into threes and find the intersect
        return read().map { it.toCharArray() }
                .windowed(BADGED_GROUP_SIZE, BADGED_GROUP_SIZE, false)
                .map {group ->
                    // this will fall apart as soon as the group size is not 3
                    group[0].intersect(group[1].toSet()).intersect(group[2].toSet())
                }
                .flatMap { chars -> chars.map { mapToPriority(it) } }
                .sum()
    }

    private fun read(): Sequence<String> = reader.readNonEmptyLines(fileName).map {it.trim()}

    companion object {
        private fun mapToPriority(c: Char): Int {
            return if (c.isLowerCase()) {
                c.toInt()-96
            } else {
                c.toInt()-38
            }
        }

        private const val BADGED_GROUP_SIZE = 3
    }
}