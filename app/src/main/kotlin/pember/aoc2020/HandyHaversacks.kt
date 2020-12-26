package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 7
 */
class HandyHaversacks(private val fileName: String) {

    private val colorRules = mutableMapOf<String, List<BagRule>>()

    init {
        FileReader.readLines(fileName)
            .asSequence()
            .filter {it.isNotBlank()}
            .forEach { apply(it) }
        println("lookup = ${colorRules}")
    }

    fun possibleBagsThatHold(color: String): Int {
        return colorRules.keys
            .filter {it != color}
            .map {
                searchHolderBags(it, color, mutableSetOf()).size
            }
            .filter { it > 0 }
            .count()
    }

    fun bagCountPerOneOf(color: String): Int = colorRules[color]!!
        .map { bagRule ->
            bagRule.count + (bagRule.count * bagCountPerOneOf(bagRule.color))
        }
        .sum()


    private fun searchHolderBags(rootColor: String, targetColor: String, collectedRoots: MutableSet<String>): MutableSet<String> {
        colorRules[rootColor]!!.forEach {
            if (it.color == targetColor) {
                collectedRoots.add(rootColor)
            }
            searchHolderBags(it.color, targetColor, collectedRoots)
        }
        return collectedRoots
    }

    private fun apply(line: String) {
        // color
        val color = SUBJECT_COLOR_MATCHER.matchEntire(line)!!.groupValues[1]
        val holdingColors = mutableListOf<BagRule>()
        val findAllResult = HOLDS.findAll(line)
        findAllResult.forEach {
            holdingColors.add(BagRule(color = it.groupValues[2], count = it.groupValues[1].toInt()))
        }
        colorRules[color] = holdingColors
    }

    private data class BagRule(val color: String, val count: Int)

    companion object {
        private val SUBJECT_COLOR_MATCHER = "^(\\w+ \\w+) bags? contain.*".toRegex()
        private val HOLDS = "(\\d) (\\w+ \\w+) bags?".toRegex()
    }
}
