package pember.challenges.aoc.aoc2020

import pember.utils.FileReader

/**
 *
 */
object PasswordCheck {

    fun numValidRange(inputFile: String): Int =
        loadPasswords(inputFile)
            .filter { it.isWithinRange() }
            .count()


    fun numValidPosition(inputFile: String): Int =
        loadPasswords(inputFile)
            .filter { it.adheresToPosition() }
            .count()

    private fun loadPasswords(inputFile: String): Sequence<PasswordContext> {
        val lines = FileReader.readLines(inputFile)
        return sequence {
            lines
                .filter { it.isNotBlank() }
                .forEach {
                    yield(PasswordContext(it))
                }
        }
    }

    private class PasswordContext(private val inputStr: String) {
        var min: Int = 0
        var max: Int = 0
        var requiredCharacter: Char = 'a'
        var password: String = ""
        init {
            val components = inputStr.split(" ")
            // grab min / max
            val minData = components[0].split("-")
            min = minData[0].toInt()
            max = minData[1].toInt()

            requiredCharacter = components[1][0]

            password = components[2]
        }

        fun countofChar(): Int  = password.count { it == requiredCharacter }

        fun isWithinRange(): Boolean = countofChar() in min..max

        /**
         * Each policy actually describes two positions in the password, where 1 means the first character,
         * 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!)
         * Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.


         */
        fun adheresToPosition(): Boolean {
            val pos1 = password[min-1]
            val pos2 = password[max-1]
            // pos1 or pos2 == requiredChar
            if (pos1 == requiredCharacter && pos2 == requiredCharacter) {
                return false
            } else {
                return pos1 == requiredCharacter || pos2 == requiredCharacter
            }
        }

        override fun toString(): String {
            return "cxt -> '${requiredCharacter}' ${min}/${max} -> ${password}"
        }

    }
}
