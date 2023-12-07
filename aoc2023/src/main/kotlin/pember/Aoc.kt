package pember

import pember.aoc2023.WaitForIt

class Aoc {
    fun runPuzzle(day: Int) {
        when(day){
            6 -> {
                println(WaitForIt.waysToWin("day6-test.txt"))
                println(WaitForIt.waysToWin("day6.txt"))
            }
            else -> {
                println("unknown day $day")
            }
        }


    }
}

fun main(args: Array<String>) {
    println("data -> ${args.toList()}")
    Aoc().runPuzzle(Integer.parseInt(args.first()))
}