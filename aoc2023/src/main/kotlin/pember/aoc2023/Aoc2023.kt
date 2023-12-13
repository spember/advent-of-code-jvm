package pember.aoc2023

import pember.utils.YearlyFileReader

abstract class Aoc2023(val day: Int) {
    protected val reader = YearlyFileReader(2023)

    protected fun fn(part: String): String = "day$day$part.txt"

}