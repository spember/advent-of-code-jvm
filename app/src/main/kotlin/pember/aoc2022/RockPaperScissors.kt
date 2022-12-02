package pember.aoc2022

class RockPaperScissors(private val fileName:String): Aoc2022() {

    private fun basicRead(): Sequence<List<String>> =
            reader.readLines(fileName)
                    .filter { it.isNotEmpty() }
                    .map {it.trim().split(" ") }

    fun getNaiveTotalScore(): Int {
        return basicRead()
                .map { naiveLookup[it.first()]!! to naiveLookup[it.last()]!!}
                .map {
                    val outcome = resolveMyOutcome(it.first, it.second)
                    outcome.score + it.second.score
                }
                .sum()
    }

    fun getUltraSecretStrategyScore(): Int {
        return basicRead()
                .map {
                    val opChoice = naiveLookup[it.first()]!!
                    val myChoice = selectChoiceForOutcome(opChoice, secretLookup[it.last()]!!)
                    opChoice to myChoice
                }
                .map { resolveMyOutcome(it.first, it.second).score + it.second.score}
                .sum()

    }

    private fun resolveMyOutcome(left: Choices, me: Choices): Outcome {
        when (left) {
            Choices.ROCK -> {
                return when(me) {
                    Choices.ROCK -> Outcome.DRAW
                    Choices.PAPER -> Outcome.WIN
                    Choices.SCISSORS -> Outcome.LOSE
                }
            }
            Choices.SCISSORS -> {
                return when (me) {
                    Choices.ROCK -> Outcome.WIN
                    Choices.PAPER -> Outcome.LOSE
                    Choices.SCISSORS -> Outcome.DRAW
                }
            }
            else -> { // left = PAPER
                return when (me) {
                    Choices.ROCK -> Outcome.LOSE
                    Choices.PAPER -> Outcome.DRAW
                    Choices.SCISSORS -> Outcome.WIN
                }
            }
        }
    }

    private fun selectChoiceForOutcome(opChoice: Choices, desiredOutcome: Outcome): Choices {
        when (opChoice) {
            Choices.ROCK ->
                return when (desiredOutcome) {
                    Outcome.WIN -> Choices.PAPER
                    Outcome.LOSE -> Choices.SCISSORS
                    Outcome.DRAW -> Choices.ROCK
                }
            Choices.PAPER ->
                return when (desiredOutcome) {
                    Outcome.WIN -> Choices.SCISSORS
                    Outcome.LOSE -> Choices.ROCK
                    Outcome.DRAW -> Choices.PAPER
                }
            Choices.SCISSORS ->
                return when (desiredOutcome) {
                    Outcome.WIN -> Choices.ROCK
                    Outcome.LOSE -> Choices.PAPER
                    Outcome.DRAW -> Choices.SCISSORS
                }
        }
    }



    companion object {
        private enum class Outcome(val score: Int) {
            WIN(6),
            LOSE(0),
            DRAW(3)
        }

        private enum class Choices(val score: Int) {
            ROCK(1),
            PAPER(2),
            SCISSORS(3)
        }

        private val naiveLookup: Map<String, Choices> = mapOf(
        "A" to Choices.ROCK,
        "B" to Choices.PAPER,
        "C" to Choices.SCISSORS,
        "X" to Choices.ROCK,
        "Y" to Choices.PAPER,
        "Z" to Choices.SCISSORS
        )

        private val secretLookup: Map<String, Outcome> = mapOf(
                "X" to Outcome.LOSE,
                "Y" to Outcome.DRAW,
                "Z" to Outcome.WIN
        )
    }

}