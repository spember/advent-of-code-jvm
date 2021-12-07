package pember.aoc2021

import pember.utils.YearlyFileReader
import java.lang.Integer.min

class TheTreacheryOfWhales(private val fileName: String) {

    fun alignCrabs(): Int {
        // just eye-balling it, the median looks like it's the right answer
        val nums = parseNums()
        val median = nums.sorted()[nums.size/2]
        val fuel = calculateFuel(nums, median) {distance -> distance}
        println("median: ${median} and fuel = $fuel")
        return fuel
    }

    fun alignCrabsTricky(): Int {
        // coincidentally, I also played around with the mean for problem 1 and it turns out to be right for problem 2
        val nums = parseNums()
        val meanUp = Math.round(nums.average())
        println("Nums: ${nums.size} -> $meanUp")

        val fuelUp = calculateFuel(nums, meanUp.toInt()) { distance -> sumFuel(distance)}
        // buuut... I got an error with rounding up. So tried rounding down (or up and then down 1... and it worked)
        // wild guess
        println("median: ${meanUp} and fuel = $fuelUp")
        val fuelDown = calculateFuel(nums, (meanUp-1).toInt()) { distance -> sumFuel(distance)}
        println("median: ${meanUp-1} and fuel = $fuelDown")
        // I think I got lucky here, with the true answer being the median rounded either up or down.
        return min(fuelUp, fuelDown)
    }

    private fun calculateFuel(nums: List<Int>, offset: Int, fuelCalc: (Int) -> Int): Int {
        return nums
            .map { Math.abs(it-offset)}
            .map ( fuelCalc )
            .sum()
    }

    private fun sumFuel(distance: Int): Int {
        return 0.rangeTo(distance).sum()
    }

    private fun parseNums(): List<Int> =
        YearlyFileReader(2021).readNonEmptyLines(fileName).take(1)
            .flatMap { raw -> raw.split(",").map {it.toInt()}}
            .toList()
}