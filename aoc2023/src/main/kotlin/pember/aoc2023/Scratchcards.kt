package pember.aoc2023

object Scratchcards: Aoc2023() {

    fun scoreEach(fn: String): Int {
        val result = reader.readLines(fn)
            .map { reader.extractIdAndSegments(it, "Card ", "|") }
            .map {(id, segments) -> Card(id, segments) }
            .map { it.score().first }
            .sum()
        println("score -> $result")
        return result
    }

    fun cardBananza(fn: String): Int {
        val cardBank: MutableList<Pair<Card, Int>> = reader.readLines(fn)
            .map { reader.extractIdAndSegments(it, "Card ", "|") }
            .map {(id, segments) -> Card(id, segments) }
            .map { it to 1 }
            .toMutableList()
        println("Here we go")
        // for each card, find the number of wins.
        for (pos in cardBank.indices) {
            val currentCard = cardBank.get(pos)
            val wins = currentCard.first.score().second
            // increase each subsequent card by copies
            for (i in 1..wins) {
                cardBank[pos+i] = cardBank[pos+i].first to cardBank[pos+i].second+currentCard.second
            }
        }
        return cardBank.sumOf { it.second }
    }

    private class Card(val id: Int, parts: List<String>) {
        var winningNumbers: List<Int>
        var cardScores: List<Int>
        init {
            winningNumbers = parts.first().split(" ").filter { it.isNotEmpty() }.map { Integer.parseInt(it.trim()) }
            cardScores = parts.last().split(" ").filter { it.isNotEmpty() }.map { Integer.parseInt(it.trim()) }
        }

        /**
         * result is score to count
         */
        fun score(): Pair<Int, Int> {

            val wins = winningNumbers.intersect(cardScores)
            if (wins.isEmpty()) {
                return 0 to 0;
            }
            val score = Math.pow(2.0, wins.size.toDouble()-1)
            return score.toInt() to wins.size
        }

    }

}