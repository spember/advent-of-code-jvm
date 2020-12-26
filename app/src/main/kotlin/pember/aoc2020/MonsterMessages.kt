package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 19
 *
 */
class MonsterMessages(fileInput: String, private val usingAdvancedRules:Boolean=false) {

    private val rules = mutableMapOf<Int, Rule>()
    private val checks: List<String>
    private val ruleCache = mutableMapOf<Int, String>()
    private val rule0Matcher: Regex

    init {
        val terminalMatcher = "^\\s*\"(\\w)\"" .toRegex()
        val parts = FileReader.readLinesIntoGroups(fileInput)


        var ruleCount = 0
        parts.first().filter{it.isNotEmpty()}.forEach {
            ruleCount += 1
            val halves = it.split(":")
            val rule: Rule

            val terminalMatch = terminalMatcher.matchEntire(halves[1])
            if (terminalMatch != null) {
                rule = TerminalRule(terminalMatch.groupValues[1])
            } else {
                val ruleList: MutableList<List<Int>> = mutableListOf()
                halves[1].trim().split(" | ")
                    .forEach {innerRule ->
                        ruleList.add(innerRule.split(" ").map {it.toInt()}.toList())
                    }
                rule = ExtensionRule(ruleList)
            }
            rules[halves[0].toInt()] = rule
        }
        if (usingAdvancedRules) {
            rules[8] = ExtensionRule(listOf(listOf(42), listOf(42,8)))
            rules[11] = ExtensionRule(listOf(listOf(42, 31), listOf(42,11,31)))
        }
        rule0Matcher = search(0).toRegex()
        println("My regex is ${rule0Matcher}")
        checks = parts.drop(1).first()
    }

    /**
     * Returns the number of matching rules in the input
     */
    fun matchChecks(): Int =
        checks.filter { rule0Matcher.matchEntire(it) != null }.count()


    private fun search(position: Int): String {
        if (ruleCache.containsKey(position)) {
            return ruleCache[position]!!
        }
        /*
        8: 42 | 42 8
        11: 42 31 | 42 11 31
         */
        if (position == 8 && usingAdvancedRules) {
            println("it's rule 8!")
            return searchIndividual(42) + "+"
        }
        if (position == 11 && usingAdvancedRules) {
            println("it's rule 11!")
            val s42 = searchIndividual(42)
            val s31 = searchIndividual(31)
            // throwing in the towel! this works for the test input, but does not for my input
            // I think the correct answer is recursive regexp, which after a google search shows that the JVM does not
            // support. so. blah
            return "$s42{1,}$s31{1,}"
        }

        val rule = rules[position]!!
        if(rule::class.java == TerminalRule::class.java) {
            return (rule as TerminalRule).letter
        }
        val extensionRule = (rule as ExtensionRule)
        return if (extensionRule.moreRules.size == 1) {
            // builds string without or'ing
            processRules(extensionRule.moreRules.first())
        } else {
            "(" + extensionRule.moreRules.map { processRules(it) }.joinToString("|") +")"
        }
    }

    private fun processRules(rulePositions: List<Int>): String =
        rulePositions.map { nextPosition ->
            searchIndividual(nextPosition)
        }
        .joinToString("")

    private fun searchIndividual(rulePosition: Int): String {
        val query = search(rulePosition)
        if (!ruleCache.containsKey(rulePosition)) {
            ruleCache[rulePosition] = query
        }
        return query
    }
    /*
    For part 2, rule 8 is just adding the + operator to the pattern for rule 42. Rule 11 was trickier and got me
    reading up on recursive regex patterns. The standard library re package doesn't support this, but the drop-in replacement called regex has support for this and more. Rule 11 is then built like so:
     */
    interface Rule

    class ExtensionRule(val moreRules: List<List<Int>>): Rule {

        override fun toString(): String = "${moreRules}"
    }

    class TerminalRule(val letter: String): Rule {

        override fun toString(): String = " \"$letter\" "
    }
}
