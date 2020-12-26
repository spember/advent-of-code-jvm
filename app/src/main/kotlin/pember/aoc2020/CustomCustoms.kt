package pember.challenges.aoc.aoc2020

import pember.utils.FileReader

/**
 * Day 6
 */
object CustomCustoms {

    fun findCountOfYesses(fileName: String): Int =
        getGroups(fileName)
            .map { group -> group.numberOfYesInGroup() }
            .sum()

    fun findCountOfUnanimousAnswers(fileName: String): Int =
       getGroups(fileName)
           .map { group -> group.numberOfUnanimousYeses() }
           .sum()

    private fun getGroups(fileName: String):Sequence<CustomGroup> =
        FileReader.readLinesIntoGroups(fileName)
            .map { groupAnswers -> CustomGroup(groupAnswers) }


    class CustomGroup(answers: List<String>) {
        // encapsulate the customs questions around a map of question -> occurrences within the group
        private val answerStore = mutableMapOf<Char, Int>()
        private val peopleInGroup = answers.size

        init {
            answers
                .map { inputLine -> inputLine.toList() }
                .flatten()
                .filter { answer -> answer.isLowerCase() } // just in ... case
                .forEach { answer -> answerStore[answer] = answerStore.getOrDefault(answer, 0) + 1 }
        }

        fun numberOfYesInGroup(): Int = answerStore.keys.size

        fun numberOfUnanimousYeses(): Int =
            answerStore.values
                .filter { it == peopleInGroup }
                .count()
    }
}
