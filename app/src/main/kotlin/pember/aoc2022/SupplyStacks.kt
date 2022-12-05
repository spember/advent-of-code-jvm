package pember.aoc2022

import java.util.LinkedList

class SupplyStacks(private val fileName: String):Aoc2022() {

    fun findTopCrates(): String =
        operateCrane {stacks, instruction ->
            for(i in 0 until instruction.count) {
                val crate = stacks[instruction.move.first].poll()
                stacks[instruction.move.second].push(crate)
            }
        }

    fun findTopCratesWith9001(): String =
       operateCrane { stacks, instruction ->
           val localStack = mutableListOf<Char>()
           for(i in 0 until instruction.count) {
               localStack.add(stacks.get(instruction.move.first).poll())
           }
           localStack.reversed().forEach {
               stacks.get(instruction.move.second).push(it)
           }
       }

    private fun operateCrane(instructionHandler: (stacks:Array<LinkedList<Char>>, instruction:Instruction)->Unit): String {
        val groups = this.reader.readLinesIntoGroups(fileName)
        val stacks = createStacks(groups.first())
        println("We have ${stacks.size} stacks")
        val instructions = parseInstructions(groups.last())
        instructions.forEach { instructionHandler.invoke(stacks, it) }
        return stacks.mapNotNull { it.peek() }
            .toList().joinToString("")
    }



    companion object {
        private fun createStacks(input: List<String>): Array<LinkedList<Char>> {
            val setup = input.map { it.windowed(3, 4, true)}
            val stacks: Array<LinkedList<Char>> = Array(setup.last().size) { LinkedList() }

            setup.take(setup.size-1)
                .forEach {row ->
                    row.forEachIndexed {pos, value ->
                        if(value.isNotBlank()) {
                            stacks.get(pos).add(value.get(1))
                        }

                    }
                }
            return  stacks
        }

        private fun parseInstructions(input: List<String>): List<Instruction> {
            return input.map {it.split(" ")}
                .map {Instruction(it[1].toInt(), it[3].toInt()-1 to it.last().toInt()-1)}
        }
    }

    private data class Instruction(val count: Int, val move: Pair<Int, Int>)
}