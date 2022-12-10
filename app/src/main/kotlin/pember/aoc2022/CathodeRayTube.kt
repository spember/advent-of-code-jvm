package pember.aoc2022

class CathodeRayTube(private val fileName: String): Aoc2022() {

    private val instructions = reader.readNonEmptyLines(fileName).map {it.split(" ")}
    private val radio = Radio(1)

    fun findSignalStrength(): Int {
//        instructions.forEach { println(it.size) }
        val signalObserver = SignalObserver()
        radio.register(signalObserver)

        instructions.forEach { radio.receive(it) }

        return signalObserver.getSum()
    }


    private class Radio(var register: Int) {

        private val observers = mutableListOf<SignalObserver>()
        private var cycleCount = 0

        fun register(signalObserver: SignalObserver) {
            observers.add(signalObserver)
        }

        fun receive(instruction: List<String>) {
            when {
                instruction.first() == NOOP -> {
                    cycle(1, 0)
                }
                instruction.first() == ADDX -> {
                    cycle(2, instruction.last().toInt())
                }
                else -> {
                    println("Unknown instruction ${instruction.first()}")
                }
            }
        }

        private fun cycle(count: Int, addValue: Int) {
            for (c in 0 until count) {
                cycleCount++
                this.observers.forEach { it.scan(cycleCount, register) }
            }
            register += addValue
        }

        companion object {
            private const val NOOP = "noop"
            private const val ADDX = "addx"
        }
    }

    private class SignalObserver {
        private val signals: MutableList<Int> = mutableListOf()
        private var nextCycleTarget = 20
        fun scan(currentCycle: Int, register: Int) {
            if (currentCycle == nextCycleTarget) {
                println("I care about this! at ${currentCycle}, register is ${register}")
                println("adding ${currentCycle*register} to signals and setting next target to ${nextCycleTarget+ TARGET_INC}")
                signals.add(currentCycle*register)
                nextCycleTarget += TARGET_INC

            }
        }
        fun getSum() = signals.sum()

        companion object {
            private const val TARGET_INC = 40
        }
    }


}