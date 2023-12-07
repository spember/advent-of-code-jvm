package pember.aoc2023

import java.lang.RuntimeException

object CamelCards: Aoc2023(7) {

    fun part1() {
        println("Running AOC day $day part 1")
        val p1Test = countTotalWinnings("day$day-test.txt")
        println("Part 1 test -> $p1Test should equal 6440")
        assert(p1Test==6440L)

        val p1Result = countTotalWinnings("day$day.txt")
        println("Part 1 result -> $p1Result should equal 241344943")
        assert(p1Result==241344943L)
    }

    fun part2() {
        println("Running AOC day $day part 2")
        val p2Test = countTotalWinnings("day$day-test.txt", true)
        println("part 2 test -> $p2Test should equal 5905")
        assert(p2Test == 5905L)

        val p2Result = countTotalWinnings("day$day.txt", true)
        println("Part 2 result -> $p2Result should equal 243101568")
        assert(p2Result == 243101568L)

    }

    private fun countTotalWinnings(fn: String, wild:Boolean=false): Long {
        val hands: MutableList<Hand> = reader.readLines(fn).map { it.split(" ") }
            .map {
                Hand(it[0].toList().map { Card.findBySymbol(it) }, Integer.parseInt(it[1]), wild)
            }.toMutableList()

//        hands.sorted().forEach {
//            println(it.cards)
//        }
        return hands.sorted().mapIndexed {i,v ->
            v.bid.toLong() * (i+1)
        }
            .sum()
    }

    private class Hand(val cards: List<Card>, val bid: Int, val withWild: Boolean=false): Comparable<Hand> {
        private val cardCounts: MutableMap<Card, Int> = Card.values().associateWith { 0 }.toMutableMap()
        var type: Scenario
        init {
            cards.forEach { cardCounts[it] = cardCounts[it]!! + 1 }
            type = if(!withWild) {
                findType()
            } else {
                findTypeWithJokers()
            }
        }

        private fun findType(): Scenario {
            // convert to a set and find the uniques
            val setCheck = cardCounts.values.filter { it > 0 }.sorted()
            return if (setCheck.last() == 5) {
                Scenario.FIVE_KIND
            } else if (setCheck.last() == 4) {
                Scenario.FOUR_KIND
            } else if (setCheck == listOf(2, 3)) {
                Scenario.FULL_HOUSE
            } else if (setCheck == listOf(1, 1, 3)) {
                Scenario.THREE_KIND
            } else if (setCheck == listOf(1, 2, 2)) {
                Scenario.TWO_PAIR
            } else if (setCheck == listOf(1, 1, 1, 2)) {
                Scenario.ONE_PAIR
            } else {
                Scenario.HIGH_CARD
            }
        }

        private fun findTypeWithJokers(): Scenario {
                val jokercount = cardCounts[Card.JOKER]!!
                if(jokercount == 0) {
                    return findType()
                }
                val result = cardCounts.entries.filter { it.component1() != Card.JOKER }
                    .map { it.component2() }
                    .filter { it > 0 }.sorted()
                if (result.isEmpty() || result.last() == 4) {
                    return Scenario.FIVE_KIND
                } else if (result.last() == 3) {
                    return if (jokercount >= 2) {
                        Scenario.FIVE_KIND
                    } else {
                        Scenario.FOUR_KIND
                    }
                } else if (result == listOf(2,2)) {
                    // 1 joker
                    return Scenario.FULL_HOUSE
                } else if (result.last() == 2) { // one pair
                    return when(jokercount) {
                        3 -> Scenario.FIVE_KIND
                        2 -> Scenario.FOUR_KIND
                        1 -> Scenario.THREE_KIND
                        else -> {throw RuntimeException("impossible one pair scenario")}
                    }
                } else {
                    return when(jokercount) {
                        4 -> Scenario.FIVE_KIND
                        3 -> Scenario.FOUR_KIND
                        2 -> Scenario.THREE_KIND
                        1 -> Scenario.ONE_PAIR
                        else -> {
                            throw RuntimeException("impossible high card wild scenario ${cards}, ${jokercount}")
                        }
                    }
                }
                // get joker count, remove jokers then get cards remaining
                // if joker count == 0, return findType
                // otherwise...
                /*
                    high card -> 4 jokers -> 5 of a kind
                                3 jokers -> 4 of a kind
                                2 jokers -> 3 of a kind
                                1 joker -> 1 pair

                    one pair -> 3 jokers -> 5 of a kind
                                2 jokers -> 4 of a kind
                                1 joker -> 2 pair
                     two pair -> 1 joker -> full house
                     three of a kind -> 2 jokers -> 5 of a kind
                                    -> 1 j -> 4 of a kind
                     4 of a kind -> 1 joker -> 5 of a kind
                 */
        }

        override fun compareTo(other: Hand): Int {
            return if (this.type.weight < other.type.weight) {
                -1
            } else if (this.type.weight > other.type.weight) {
                1
            } else {
                compareHand(other)
            }
        }

        /**
         * return 0 if hands are the same, -1 if this hand is less than other, or 1 if this is larger than other
         */
        fun compareHand(other: Hand): Int {
            for (i in cards.indices) {
                val w1 =  calculateWeight(this.cards[i])
                val w2 = calculateWeight(other.cards[i])

                if (w1 > w2) {
                    return 1
                } else if (w1 < w2) {
                    return -1
                }
            }
            return 0
        }

        private fun calculateWeight(card: Card): Int {
            return if (withWild && Card.JOKER == card) {
                -1
            } else {
                card.weight
            }
        }
    }



    private enum class Card(val symbol: Char, val weight: Int) {
        ACE('A', 14),
        KING('K', 13),
        QUEEN('Q', 12),
        JOKER('J', 11),
        TEN('T', 10),
        NINE('9', 9),
        EIGHT('8', 8),
        SEVEN('7', 7),
        SIX('6', 6),
        FIVE('5', 5),
        FOUR('4', 4),
        THREE('3', 3),
        TWO('2', 2);

        companion object {
            fun findBySymbol(target: Char): Card {
                Card.values().forEach { if (it.symbol == target) {
                    return it
                    }
                }
                throw RuntimeException("No card with symbol $target")
            }
        }

    }

    private enum class Scenario(val weight:Int) {
        FIVE_KIND(5),
        FOUR_KIND(4),
        FULL_HOUSE(3),
        THREE_KIND(2),
        TWO_PAIR(1),
        ONE_PAIR(0),
        HIGH_CARD(-1)
    }
}