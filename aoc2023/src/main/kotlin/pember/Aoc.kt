package pember

import pember.aoc2023.*

class Aoc {
    fun runPuzzle(day: Int) {
        println("Welcome to AOC 2023 day $day")
        when(day){
            6 -> {
                println(WaitForIt.waysToWin("day6-test.txt"))
                println(WaitForIt.waysToWin("day6.txt"))
            }
            7 -> {
                CamelCards.part1()
                CamelCards.part2()
            }
            8 -> {
                HauntedWasteland.part2()
            }
            9 -> {
                MirageMaintenence.part2()
            }
            10 -> {
                PipeMaze.part2()
            }
            11 -> {
                CosmicExpansion.part1()
                CosmicExpansion.part2()
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