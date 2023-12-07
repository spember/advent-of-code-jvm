package pember.utils

class YearlyFileReader(private val year: Int) {
    /**
     * Read the given aoc2020 file as a [Sequence] of lines
     */
    fun readLines(fileName: String): Sequence<String>  =
        this.javaClass.getResource("/aoc${year}/${fileName}")
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
        var currentLines = mutableListOf<String>()
        return sequence {
            readLines(fileName).asSequence().forEach {
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

    fun readLinesIntoCharGrid(fileName: String): Array<CharArray> {
        return readLines(fileName).map { it.toCharArray() }.toList().toTypedArray()
    }

    fun extractIdAndSegments(line: String, idLabel: String, segmentsDelim: String): Pair<Int, List<String>> {
        val pieces = line.split(":")
        val id = Integer.parseInt(pieces[0].removePrefix(idLabel).trim())
        val segments = pieces[1].split(segmentsDelim)
        return id to segments
    }
}