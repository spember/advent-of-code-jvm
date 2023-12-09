package pember.aoc2023

import java.lang.RuntimeException
import java.util.LinkedList

object MirageMaintenence: Aoc2023(9) {

    fun part1() {
        val test = extrapolateFuture("day$day-test.txt")
        println("p1 test = $test")
        if(test != 114) {
            throw RuntimeException("not equal")
        }

        val real = extrapolateFuture("day$day.txt")
        println("p1 = $real should be 2043183816")

    }

    fun part2() {
        val test = extrapolatePast("day$day-test.txt")
        println("p2 test = $test should be 2")

        val real = extrapolatePast("day$day.txt")
        println("real = $real should be 1118")
    }

    private fun extrapolateFuture(fn: String): Int = parseLines(fn)
            .map { history ->
                runSequence(history).scan(0) { acc, i ->  acc+i }.last()
            }.sum()

    private fun extrapolatePast(fn: String): Int = parseLines(fn)
            .map { history ->
                runSequence(history, false).scan(0) {acc, i ->  i-acc}.last()
            }
            .sum()

    private fun parseLines(fn: String): Sequence<List<Int>> = reader.readLines(fn)
    .map { it.trim().split(" ").map { Integer.parseInt(it) } }

    private fun runSequence(history: List<Int>, getLast:Boolean=true): LinkedList<Int> {
//        println(history)
        var next = history
        val lastValues = LinkedList<Int>()
        while(true) {
            if (getLast){
                lastValues.push(next.last())
            } else {
                lastValues.push(next.first())
            }

//                println("\t" + next.zipWithNext{a,b -> b-a})
            next = next.zipWithNext{a,b -> b-a}
            if (next.isEmpty()) {
                break
            }
        }
        return lastValues
    }
}