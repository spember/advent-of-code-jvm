package pember.aoc2020

import pember.utils.FileReader

/**
 *
 */
class RainRisk(private val fileName: String) {


    private val ship = Ship()
    private val waypoint = Waypoint()

    fun sail(): RainRisk {
        FileReader.readLines(fileName = fileName)
            .filter { it.isNotBlank() }
            .map { Instruction(it) }
            .forEach { instruction -> ship.respondToInstruction(instruction) }
        return this
    }

    fun sailWithWaypoint(): RainRisk {
        FileReader.readLines(fileName = fileName)
            .filter { it.isNotBlank() }
            .map { Instruction(it) }
            .forEach { instruction ->
                if (instruction.command == FORWARD) {
                    ship.moveToWaypoint(waypoint, instruction.magnitude)
                } else {
                    waypoint.respondToInstruction(instruction)
                }
            }
        return this
    }

    fun manhattanDistance(): Int = ship.manhattanDistance()

    private class Instruction(rawInput: String) {
        val command: Char = rawInput[0].toUpperCase()
        val magnitude: Int = rawInput.slice(1 until rawInput.length).toInt()
    }

    private class Ship(): SeaFairer(0, 0 ) {
        var direction = 90 // East
            private set

        fun manhattanDistance(): Int = Math.abs(northerly)+Math.abs(easterly)

        fun moveToWaypoint(waypoint: Waypoint, times: Int) {
            for (i in 0 until times) {
                northerly += waypoint.northerly
                easterly += waypoint.easterly
            }
        }

        override fun rotateLeft(magnitude: Int) {
            direction -= magnitude
            if (direction < 0) {
                direction += 360
            }
        }

        override fun rotateRight(magnitude: Int) {
            direction += magnitude
            if (direction > 360) {
                direction -= 360
            }
        }

        override fun rightAhead(magnitude: Int) {
            // only 90 degrees?
            when(direction) {
                0 -> moveNorth(magnitude)
                360 -> moveNorth(magnitude) // <-- in case I screw this up
                90 -> moveEast(magnitude)
                180 -> moveSouth(magnitude)
                270 -> moveWest(magnitude)
                else -> {
                    println("Unknown direction ${direction}")
                }
            }
        }
    }

    private class Waypoint: SeaFairer(1, 10) {
        override fun rotateLeft(magnitude: Int) {
            executeRotation(magnitude) {
                northerly*-1 to easterly
            }
        }

        override fun rotateRight(magnitude: Int) {
            executeRotation(magnitude) {
                northerly to easterly*-1
            }
        }

        private fun executeRotation(magnitude: Int, calculate: () -> Pair<Int, Int>) {
            val times = magnitude/90
            for (i in 0 until times) {
                val updated = calculate()
                easterly = updated.first
                northerly = updated.second
            }
        }

        override fun rightAhead(magnitude: Int) {
            TODO("Not yet implemented")
        }


    }

    private abstract class SeaFairer(initialN: Int, initialE: Int) {
        var northerly = initialN
            protected set
        var easterly = initialE
            protected set

        protected fun moveNorth(magnitude: Int) { northerly += magnitude }
        protected fun moveSouth(magnitude: Int) { northerly -= magnitude }

        protected fun moveEast(magnitude: Int) { easterly += magnitude }
        protected fun moveWest(magnitude: Int) { easterly -= magnitude }

        protected abstract fun rotateLeft(magnitude: Int)

        protected abstract fun rotateRight(magnitude: Int)

        protected abstract fun rightAhead(magnitude: Int)

        fun respondToInstruction(instruction: Instruction) {
            when(instruction.command) {
                LEFT -> rotateLeft(instruction.magnitude)
                RIGHT -> rotateRight(instruction.magnitude)

                NORTH -> moveNorth(instruction.magnitude)
                SOUTH -> moveSouth(instruction.magnitude)

                EAST -> moveEast(instruction.magnitude)
                WEST -> moveWest(instruction.magnitude)

                FORWARD -> rightAhead(instruction.magnitude)
            }
        }
    }



    companion object {
        private const val NORTH ='N'
        private const val EAST = 'E'
        private const val SOUTH = 'S'
        private const val WEST = 'W'

        private const val LEFT = 'L'
        private const val RIGHT = 'R'
        private const val FORWARD = 'F'
    }
}
