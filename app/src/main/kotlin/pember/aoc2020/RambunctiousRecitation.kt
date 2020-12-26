package pember.aoc2020

/**
 * Day 15
 * https://adventofcode.com/2020/day/15
 */
object RambunctiousRecitation {

    fun basicSpeaking(initialGuesses: IntArray, target: Int): Int {
        val guesses: MutableMap<Int, GuessContext> = mutableMapOf()
        var previouslySpoken = 0
        var spokenThisTurn = 0
        initialGuesses.forEachIndexed { index, guess ->
            guesses[guess] = GuessContext(1, index to index)
            previouslySpoken = guess
        }
        println("seed = ${guesses.keys.size}, prev: ${previouslySpoken}")

        // previously spoken is at turn i-1, current is at i
        previouslySpoken = initialGuesses.last()

        for (i in guesses.size until target) {
            // what am I going to speak?
            if (guesses[previouslySpoken]!!.count ==1) {
                spokenThisTurn = 0
            } else {
                spokenThisTurn = guesses[previouslySpoken]!!.lastTwo.first - guesses[previouslySpoken]!!.lastTwo.second
            }
            // record that I have spoken!
            if (guesses.containsKey(spokenThisTurn)) {
                guesses[spokenThisTurn]!!.count+=1
                guesses[spokenThisTurn]!!.lastTwo = i to guesses[spokenThisTurn]!!.lastTwo.first
            } else {
                guesses[spokenThisTurn] = GuessContext(1, i to i)
            }
//            println("turn ${i+1} -> previous: ${previouslySpoken}, speaking ${spokenThisTurn}. ")
            // remember what I spoke for the next iteration
            previouslySpoken = spokenThisTurn
        }
        return spokenThisTurn
    }

    // Fast and hacky context information around the 'spoken' guess. Do I even need the total count?
    // lastTwo is a pair of last and time before that. Pair might not be the best structure
    private data class GuessContext(var count: Int, var lastTwo: Pair<Int, Int>)
}
