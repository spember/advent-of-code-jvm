package pember.aoc2022

class CathodeRayTube(private val fileName: String): Aoc2022() {

    private val instructions = reader.readNonEmptyLines(fileName).map {it.split(" ")}
    private val radio = Radio(1)

    fun findSignalStrength(): Int {
        val signalObserver = SignalStrengthObserver()
        val crtObserver = CRTWriter()
        radio.register(signalObserver)
        radio.register(crtObserver)
        instructions.forEach { radio.receive(it) }
        crtObserver.draw()
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

    private interface SignalObserver {
        fun scan(currentCycle: Int, register: Int)
    }

    private class SignalStrengthObserver: SignalObserver {
        private val signals: MutableList<Int> = mutableListOf()
        private var nextCycleTarget = 20

        override fun scan(currentCycle: Int, register: Int) {
            if (currentCycle == nextCycleTarget) {
                signals.add(currentCycle*register)
                nextCycleTarget += TARGET_INC
            }
        }
        fun getSum() = signals.sum()

        companion object {
            private const val TARGET_INC = 40
        }
    }

    private class CRTWriter: SignalObserver {
        private val rows: MutableList<List<Char>> = mutableListOf()
        private var currentRow: MutableList<Char> = mutableListOf()
        private var lastCycleBreak: Int = 1

        override fun scan(currentCycle: Int, register: Int) {
            // register is middle of sprite
            // currentCycle is position
            if (currentCycle == lastCycleBreak + LINE_BREAK) {
                lastCycleBreak += LINE_BREAK
                rows.add(currentRow.toList())
                currentRow = mutableListOf()
            }
            val char = when(currentCycle-lastCycleBreak) {
                register-1 -> LIT
                register -> LIT
                register+1 -> LIT
                else -> UNLIT
            }
            currentRow.add(char)
        }

        fun draw() {
            rows.add(currentRow)
            rows.forEach { println(it.joinToString("")) }
        }

        companion object {
            private const val LINE_BREAK = 40
            private const val LIT: Char = '#'
            private const val UNLIT: Char = '.'
        }
    }


}