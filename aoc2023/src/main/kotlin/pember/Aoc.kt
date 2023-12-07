package pember

import pember.aoc2023.CamelCards
import pember.aoc2023.WaitForIt

class Aoc {
    fun runPuzzle(day: Int) {
        when(day){
            6 -> {
                println(WaitForIt.waysToWin("day6-test.txt"))
                println(WaitForIt.waysToWin("day6.txt"))
            }
            7 -> {
                CamelCards.part1()
                CamelCards.part2()
            }
            else -> {
                println("unknown day $day")
            }
        }


    }
}

fun main(args: Array<String>) {
    Aoc().runPuzzle(Integer.parseInt(args.first()))
}