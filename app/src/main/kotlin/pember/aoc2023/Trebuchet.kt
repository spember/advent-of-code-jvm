package pember.aoc2023

import java.lang.Integer.parseInt


object Trebuchet: Aoc2023() {

    fun part1(fn: String): Int = reader.readLines(fn)
        .map { it.toCharArray() }
        .map { scanIntBounds(it, it) }
        .sum()

    fun part2(fn: String): Int = reader.readLines(fn)
        .map { replaceWordsLeft(it) to replaceWordsRight(it) }
        .map { (l, r) -> scanIntBounds(l, r) }
        .sum()

    private fun scanIntBounds(left: CharArray, right: CharArray): Int {
        val first: Char = left.filter { it.isDigit() }.take(1).first()
        val last: Char = right.filter { it.isDigit() }.takeLast(1).first()
//        println("Found -> ${parseInt("${first}${last}")}")
        return parseInt("${first}${last}")
    }

    private fun replaceWordsLeft(line: String): CharArray {
        // grow left to right, looking for a replacement. If did replace, loop again
        var didReplace = true
        var target = line

        while (didReplace) {
            didReplace = false
            for (i in 2 until target.length) {

                var s = words.entries.filter { (k,v) -> target.slice(0..i).contains(k) }
                if (s.isNotEmpty()) {
                    didReplace = true
                    target = target.replace(s.first().key, s.first().value)
                    break
                }
            }
        }
        return target.toCharArray()
    }

    fun replaceWordsRight(line: String): CharArray {
        var rightwards = line
        var didReplace = true
        while(didReplace) {
            didReplace = false
            for (i in rightwards.length-2 downTo 0) {
                var s = words.entries.filter { (k,v) -> rightwards.slice(i..rightwards.length-1).contains(k) }
                if (s.isNotEmpty()) {
                    println("Replacing RIGHT ${s.first().key}")
                    didReplace = true
                    rightwards = rightwards.replace(s.first().key, s.first().value)
                    break
                }
            }
        }
        return rightwards.toCharArray()
    }

    private var words: Map<String, String> = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )
}