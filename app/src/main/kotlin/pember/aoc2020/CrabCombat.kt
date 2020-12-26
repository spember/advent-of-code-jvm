package pember.aoc2020

import pember.utils.FileReader
import java.util.LinkedList
import java.util.Queue

object CrabCombat {


    fun combat(file: String): Int {
        val (deck1, deck2) = buildInitialDecks(file)
        println("== Round 0, Fight ==")
        println(deck1)
        println(deck2)
        var rounds = 0
        while(!deck1.hasLost()&&!deck2.hasLost()) {
            rounds ++
            val d1Card = deck1.draw()
            val d2Card = deck2.draw()
            if (d1Card > d2Card) {
                // d1 wins
                deck1.insert(d1Card, d2Card)
            } else {
                deck2.insert(d2Card, d1Card)
            }
        }
        println (" == Post-game results ($rounds rounds) ==")
        println(deck1)
        println(deck2)
        println("")
        return deck1.score() + deck2.score()
    }

    fun recursiveCombat(file: String): Int {
        val (deck1, deck2) = buildInitialDecks(file)
        playRecursiveGame(1, deck1, deck2)
        println (" == Post-game results ==")
        println(deck1)
        println(deck2)
        println("")
        return deck1.score() + deck2.score()
    }

    /*
        Boolean is true if player 1 wins
     */
    private fun playRecursiveGame(gameNumber: Int, deck1: Deck, deck2: Deck): Boolean {
        val gameState = mutableListOf<Pair<List<Int>, List<Int>>>()
        var rounds = 0
        while(!deck1.hasLost()&&!deck2.hasLost()) {
            rounds++
            gameState.add(deck1.state() to deck2.state())
            val d1Card = deck1.draw()
            val d2Card = deck2.draw()

            // if should recurse, get the winner that way
            // else, check card size

            // if player 1 wins, -> add d1 card, d2 card
            // else deck2 adds d2 card, d1 card

            var player1Wins = true

            if (deck1.count() >= d1Card && deck2.count() >= d2Card) {
                player1Wins = playRecursiveGame(gameNumber +1, deck1.copy(d1Card), deck2.copy(d2Card))
            } else {
                player1Wins = d1Card>d2Card
            }

            if (player1Wins) {
                deck1.insert(d1Card, d2Card)
            } else {
                deck2.insert(d2Card, d1Card)
            }

            // escape clause
            if (isInfinite(gameState, deck1.state(), deck2.state())) {
//                println("Breaking the infinite cycle!")
                return true
            }
        }
//        println("Game ${gameNumber} complete in ${rounds} rounds. Player 1 wins? ${deck2.hasLost()}")
        return deck2.hasLost()
    }

    fun isInfinite(gameState: List<Pair<List<Int>, List<Int>>>, d1: List<Int>, d2: List<Int>): Boolean {
        for (p in gameState) {
            if (p.first == d1 && p.second == d2) {
                return true
            }
        }
        return false
    }



    private fun buildInitialDecks(file: String): Pair<Deck, Deck>  = FileReader.readLinesIntoGroups(file)
            .take(2)
            .toList()
            .map { lines ->
                val deck = Deck(lines.first().removeSuffix(":"))
                lines.drop(1).filter {it.isNotEmpty()}.forEach { deck.insert(it.toInt()) }
                deck
            }
        .windowed(2)
        .map { Pair(it[0], it[1]) }
        .first()


    class Deck(val id: String){
        private val cards: Queue<Int> = LinkedList()

        fun insert(newCard: Int): Deck {
            cards.add(newCard)
            return this
        }

        fun insert(winnerCard: Int, loserCard:Int): Deck {
            cards.add(winnerCard)
            cards.add(loserCard)
            return this
        }

        fun draw(): Int = cards.poll()

        fun hasLost(): Boolean = cards.isEmpty()

        fun count(): Int = cards.size

        fun state(): List<Int> = cards.toList()

        fun copy(quantity: Int): Deck {
            val copy = Deck("${id} -> ${quantity}")
            cards.toList().take(quantity).forEach { copy.insert(it) }
            return copy
        }

        fun score(): Int {
            var score = 0
            cards.toList().reversed().forEachIndexed {index, card ->
                score += ((index+1)*card)
            }
            return score
        }

        override fun toString(): String {
            return "${id}: ${cards.toList()}"
        }

    }
}


