package pember.aoc2021

import pember.utils.YearlyFileReader

class SevenSegmentSearch(private val fileName: String) {

    /**
     * Count the 1, 4, 7, 8s
     */
    fun focusOnEasy(): Int = parse()
            .flatMap {(_, notes) -> notes.map {it.size}}
            .count {s -> s in listOf(2, 3, 4, 7)}

    fun fullDecode(): Int {
        return parse()
            .map {(digits, notes) -> decodeLine(digits, notes)}
            .sum()
    }

    /**
     * I'm sure there's a more elegant way to do this, but I'm puzzling it out one segment at a time with set math!
     */
    private fun decodeLine(digits: List<Set<Char>>, notes: List<Set<Char>>): Int {

        val decoded = Array(7) { MISSING }

        val urOrlr = digits.first { it.size == 2 }

        decoded[0] = (digits.first {it.size == 3} - urOrlr).first()

        // four has the chars in urOrlr, plus
        val four = digits.first {it.size==4} //urOrlr
        // the symbols for three is that with 5 digits and both Upper right and lower right
        val three = digits.filter { it.size == 5 && it.containsAll(urOrlr)}.first()
        // looking at the rest of the five-length symbols...
        val fivesChunks = digits.filter { it.size == 5 }.map { (it - (four + decoded[0])) }
        // ...the common element is the bottom segment
        decoded[6] = fivesChunks.reduce { a, b -> a.intersect(b)}.first()

        val two = fivesChunks.filter {it.size == 2}.first()
        // of the 'chunks', the one with the extra segment is 2, so the lower left
        decoded[4] = (two - decoded[6]).first() // ll
        // middle one can be found from the 3 symbol, removing all overlaps
        decoded[3] = (((three-urOrlr)-(decoded[6]))-decoded[0]).first()
        // removing upper right, lower right, middle from four gives us UL
        decoded[1] = (four-urOrlr-decoded[3]).first()
        // lastly, finding the 5 symbol...
        val five = digits.filter {it.size == 5 && it.containsAll(listOf(decoded[0], decoded[1], decoded[3], decoded[6]))}.first()
        // .. can give us which one of Upper or lower right is in it
        decoded[2] = (urOrlr-five).first()
        // and then we finish up the remainder
        decoded[5] = (urOrlr-decoded[2]).first()

        return notes.fold(""){s, note ->
            s+mask(decoded, note)
        }.toInt()

        /*
            notes:
            start with 2 digit, we know that the two chars are the right two segments
            3 2 right-segments + top
            4 gives us upper left

            seven segment:
            array of size 7
            top, ul, ur, m, ll, lr, b
            [0,0,0,0,0,0,0]
            9 -> [1, 1, 1, 1, 0, 1, 1]
            8 -> [1, 1, 1, 1, 1, 1, 1]
            7 -> [1, 0, 1, 0, 0, 1, 0] (3)
            6 -> [1, 1, 0, 1, 1, 1, 1]
            5 -> [1, 1, 0, 1, 0, 1, 1]
            4 -> [0, 1, 1, 1, 0, 1, 0] (4)
            3 -> [1, 0, 1, 1, 0, 1, 1]
            2 -> [1, 0, 1, 1, 1, 0, 1]
            1 -> [0, 0, 1, 0, 0, 1, 0] (2)
            0 -> [1, 1, 1, 0, 1, 1, 1]
         */
    }

    private fun mask(decoded: Array<Char>, note: Set<Char>): Int {
        val m = Array(7) {0}
        note.forEach {c -> m[decoded.indexOf(c)] = 1}
        return DIGIT_LOOKUP[m.joinToString()]!!
    }

    private fun parse(): Sequence<Pair<List<Set<Char>>, List<Set<Char>>>> {
        return YearlyFileReader(2021)
            .readNonEmptyLines(fileName)
            .map { it.split("|") }
            // each line is an array of two
            .map { (digits, notes) ->
                Pair(
                    convertSegment(digits),
                    convertSegment(notes)
                )
            }
    }

    private fun convertSegment(segment: String): List<Set<Char>> =
        segment.trim()
            .split("\\s+".toRegex())
            .map { it.toCharArray().toSet() }

    companion object {

        private const val MISSING = '-'

        private val DIGIT_LOOKUP = mapOf<String, Int>(
            // T, UL, UR, M, LL, LR, B
            "1, 1, 1, 1, 0, 1, 1" to 9,
            "1, 1, 1, 1, 1, 1, 1" to 8,
            "1, 0, 1, 0, 0, 1, 0" to 7,
            "1, 1, 0, 1, 1, 1, 1" to 6,
            "1, 1, 0, 1, 0, 1, 1" to 5,
            "0, 1, 1, 1, 0, 1, 0" to 4,
            "1, 0, 1, 1, 0, 1, 1" to 3,
            "1, 0, 1, 1, 1, 0, 1" to 2,
            "0, 0, 1, 0, 0, 1, 0" to 1,
            "1, 1, 1, 0, 1, 1, 1" to 0

        )
    }
}