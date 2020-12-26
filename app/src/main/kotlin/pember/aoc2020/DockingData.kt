package pember.aoc2020

import pember.utils.FileReader

/**
 * Day 14
 */
class DockingData(private val fileInput: String) {

    var mem: MutableMap<Long, Long> = mutableMapOf()

    fun initializeV1() {
       initialize { currentMask, memPosition, settingValue ->
           applyMaskV1(currentMask, memPosition, settingValue)
       }
    }

    fun initializeV2() {
        initialize { currentMask, memPosition, settingValue ->
            applyMaskV2(currentMask, memPosition, settingValue)
        }
    }

    fun sumValuesInMemory(): Long {
        var sum = 0L
        mem.forEach {_, value -> sum += value}
        return sum
    }


    private fun initialize(applyMask:(CharArray, Int, Int) -> Unit) {
        mem = mutableMapOf()
        lateinit var currentMask: CharArray
        FileReader.readLines(fileInput)
            .filter { it.isNotEmpty() }
            .forEach { line ->
                if (line.startsWith("mask")){
                    currentMask = extractMask(line)
                } else {
                    val matchResult = matcher.matchEntire(line)
                    val memPosition = matchResult!!.groupValues[1].toInt()
                    val settingValue = matchResult.groupValues[2].toInt()
                    applyMask(currentMask, memPosition, settingValue)
                }
            }
    }

    private fun extractMask(line: String): CharArray  = line.split(" = ")[1].toCharArray()


    private fun applyMaskV1(mask: CharArray, memPosition: Int, settingValue: Int){
        val bitArray = convertToBitArray(settingValue)
        //X0110011000X1X110011XX110X00000101X0
        mask.forEachIndexed { index, maskBit ->
            if (maskBit != 'X') {
                bitArray[index] = maskBit
            }
        }
        mem[memPosition.toLong()] =  bitArray.joinToString("").toLong(2)
    }

    private fun applyMaskV2(mask: CharArray,  memPosition: Int, settingValue: Int) {
        val bitArray = convertToBitArray(memPosition)
        val xPositions = mutableListOf<Int>()
        mask.forEachIndexed { index, char ->
            if(char == 'X') {
                xPositions.add(index)
            } else if (char == '1') {
                bitArray[index] = '1'
            } // ignore 0
        }
        // generate a list of all possible bit permutations...

        val bitCombos = mutableSetOf<String>()
        generateBits(Array(xPositions.size){0}, 0, bitCombos)
        // for each combo, for each xPosition set to
        bitCombos.forEach { combo ->
            // then apply each one based on the position of 'x' found earlier
            // this would not work in efficient time if X is large. luckily, X Count was not 'large' for my input
            xPositions.forEachIndexed { index, position ->
                bitArray[position] = combo[index]
            }
            mem[bitArray.joinToString("").toLong(2)] = settingValue.toLong()
        }
    }

    private fun generateBits(bits: Array<Int>, position: Int, combos: MutableSet<String>) {
        combos.add(bits.joinToString("").toString())
        if (position >= bits.size) {
            return
        }
        bits[position] = 0
        generateBits(bits, position+1, combos)

        bits[position] = 1
        generateBits(bits, position+1, combos)
    }

    /**
     * converts an int to a 36 character array
     */
    private fun convertToBitArray(original: Int): CharArray {
        var bitValue = Integer.toBinaryString(original)
        while (bitValue.length < 36) {
            bitValue = "0"+bitValue
        }
        return bitValue.toCharArray()
    }

    companion object {
        private val matcher = "mem\\[(\\d+)\\] = (\\d+)".toRegex()
    }

}
