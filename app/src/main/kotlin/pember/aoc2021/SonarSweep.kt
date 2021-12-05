package pember.aoc2021

import pember.utils.YearlyFileReader

object SonarSweep {

    fun initialScan(fileName: String): Int =
        windowScan(fileName, 2, 1)
            .count(isIncrease)

    fun deeperScan(fileName: String): Int =
        windowScan(fileName, 3, 1)
            .map { it.sum() }
            .windowed(2, 1, false)
            .count(isIncrease)

    private fun windowScan(fileName: String, windowSize: Int, step: Int): Sequence<List<Int>> =
        YearlyFileReader(2021).readLinesAsInts(fileName)
            .windowed(windowSize, step, false)

    private val isIncrease: (List<Int>) -> Boolean = { (f: Int, s: Int) -> s > f }

}