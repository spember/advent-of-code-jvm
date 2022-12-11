package pember.aoc2022

import java.util.LinkedList
import kotlin.math.floor

class MonkeyInTheMiddle(private val fileName: String): Aoc2022() {

    private val monkies = reader.readLinesIntoGroups(fileName)
            .mapIndexed { index, data ->  toMonkey( index, data)  }
        .toList()



    fun calculateMonkeyBusiness(rounds:Int): Long {
        println("My monkies! ${monkies}")
        for (r in 0 until rounds) {
            for (i in monkies.indices) {
                val results = monkies[i].takeTurn()
                results.forEach { (item, monkey) ->
                    monkies[monkey].receive(item)
                }
            }
        }
        monkies.forEach { println("Inspected ${it.inspectionCount} times.") }
        val top2 = monkies.map { it.inspectionCount }.sortedDescending().take(2)
        return top2.first().toLong()*top2.last()
    }

    fun crazyMonkeyBusiness(rounds:Int): Long {
        val commonDivisor = monkies.map {it.divisibleBy}.reduce {a,b -> a*b}
        println("Common is ${commonDivisor}")
        monkies.forEach { it.divisor = commonDivisor.toLong() }
        return calculateMonkeyBusiness(rounds)
    }

    private fun toMonkey(name:Int, data: List<String>): Monkey {
        return Monkey(name,
            data[1].trim().removePrefix("Starting items: ").split(",").map { it.trim().toLong() }.toList(),
            data[2].trim().removePrefix("Operation: new = old ").split(" ")[0],
            data[2].trim().removePrefix("Operation: new = old ").split(" ")[1],
            data.subList(3,6)
        )
    }



    private class Monkey(val name: Int,
                         startingItems: List<Long>,
                         private val operationOp: String,
                         private val operationValue: String,
                         private val testLogic: List<String>,
                         var divisor:Long = 3L
                         ) {
        private val itemWorry = LinkedList(startingItems)
        val divisibleBy = testLogic.first().split(" ").last().toInt()
        private val ifTrue = testLogic[1].split(" ").last().toInt()
        private val ifFalse = testLogic[2].split(" ").last().toInt()
        var inspectionCount = 0

        fun receive(item: Long) {
            this.itemWorry.add(item)
        }

        fun takeTurn(): List<Pair<Long,Int>> {
            // build a list of items to pass around
            val results: MutableList<Pair<Long,Int>> = mutableListOf()
            while(itemWorry.isNotEmpty()) {
                var item = itemWorry.poll()
                if (divisor == 3L) {
                    item = floor(performOperation(item).toDouble() /3 ).toLong()
                } else {
                    item = performOperation(item)
                    item %= divisor
                }
                inspectionCount++
                if (item % divisibleBy == 0L) {
                    results.add(item to ifTrue)
                } else {
                    results.add(item to ifFalse)
                }
            }
            return results
        }

        private fun performOperation(old: Long): Long {
            val operand = if (operationValue == OLD) {
                old
            } else {
                operationValue.toLong()
            }
            return if (operationOp == "*") {
                old * operand
            } else {
                old + operand
            }
        }

        companion object {
            private const val OLD = "old"
        }
    }
}