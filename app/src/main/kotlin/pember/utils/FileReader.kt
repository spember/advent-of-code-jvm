package pember.utils

object FileReader {
    /**
     * Read the given aoc2020 file as a [Sequence] of lines
     */
    fun readLines(fileName: String): Sequence<String>  =
        this.javaClass.getResource("/aoc2020/${fileName}")
            // will need to parametrize this for next year
            .readText()
            .split("\n")
            .asSequence()

    /**
     * Read the given aoc2020 file as a [Sequence] of lines, ignoring empty lines
     */
    fun readNonEmptyLines(fileName: String): Sequence<String> =
        readLines(fileName)
            .filter { it.isNotEmpty() }

    /**
     * Read the given aoc2020 file as a [Sequence] of Integers
     */
    fun readLinesAsInts(fileName: String): Sequence<Int> =
        readNonEmptyLines(fileName)
            .map {it.toInt()}


    /**
     *
     */
    fun readPartial(initialGroupSize: Int, fileName: String): Pair<List<String>, Sequence<String>> {
        val lineSequence = readLines(fileName)
        return Pair(
            lineSequence.take(initialGroupSize).toList(),
            lineSequence.drop(initialGroupSize)
        )
    }


    /**
     * Often the file input would be in 'groups' of lines, separated by a simple empty line. This method separates the
     * file into sequences of these line 'groups'.
     */
    fun readLinesIntoGroups(fileName: String): Sequence<List<String>> {
        val lines = FileReader.readLines(fileName).asSequence()
        var currentLines = mutableListOf<String>()
        return sequence {
            lines.forEach {
                if (it.isEmpty()) {
                    yield(currentLines)
                    currentLines = mutableListOf()
                } else {
                    currentLines.add(it)
                }
            }
            yield(currentLines)
        }
    }
}
