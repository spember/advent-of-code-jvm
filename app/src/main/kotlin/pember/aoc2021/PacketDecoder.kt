package pember.aoc2021

import pember.utils.AocPuzzle21

class PacketDecoder(fileName: String): AocPuzzle21(fileName) {

    fun decodeHex(hexString: String): Pair<Packet, String> = decode(hexToBinary(hexString))

    fun decode(binaryString: String): Pair<Packet, String> {
        val (version, type) = getVersionAndType(binaryString)
//        println("Incoming string is ${binaryString}")
        return if (type == 4) {
            // is literal
            val (value, remainder) = parseLiteral(binaryString)
            Literal(version, type, value) to remainder
        } else {
            // it's an operator!
            val operator = Operator(version, type)
            if (binaryString.drop(6).take(1) == TOTAL_LENGTH_OPERATOR) {
                // drop 7, get 15
                val totalBits = extractLength(binaryString, 15)
//                println("totalBits: ${totalBits}")
                // extract bits and iterate, add to children, return this operator + 'outer' remainder
                var subPacketBits: String = binaryString.drop(7+15).take(totalBits)
//                println("Sub packet is ${subPacketBits}")
                while(subPacketBits.isNotEmpty()) {
                    val (child, innerRemainder) = decode(subPacketBits)
                    operator.children.add(child)
                    subPacketBits = innerRemainder
                }
                operator to binaryString.drop(7 + 15 +  totalBits)

            } else {
                // drop 7 get 11
                val subPacketCount = extractLength(binaryString, 11)
                println("Sub count = " + subPacketCount)
                var innerRemainder = binaryString.drop(7+11)
                (1..subPacketCount).forEach {
                    val (child, remainder) = decode(innerRemainder)
                    operator.children.add(child)
                    innerRemainder = remainder
                }
//                throw RuntimeException()
                return operator to innerRemainder
            }
        }
    }

    fun sumVersion(packet: Packet ): Int {
        return if (packet is Operator) {
            packet.version + (packet).children.sumBy { sumVersion(it) }
        } else {
            packet.version
        }
    }
//    fun
    // literal needs a way to extract just the number (stop at the last 0 bit) and return the remainder
    // type 0 needs to take(numberOfBits), discard any extra bits
    // type 1 needs to stop after extracting some number of packets
    // packet -> value
    // packet -> verison, type, children, in order


    companion object {
        private const val TOTAL_LENGTH_OPERATOR = "0"

        fun getVersionAndType(binaryPacket: String): Pair<Int, Int> {
            val version = fromBinary(binaryPacket.take(3))
            val type = fromBinary(binaryPacket.drop(3).take(3))
            return version to type
        }

        fun hexToBinary(hexString: String): String =
            hexString
                .map { Integer.toBinaryString(Integer.parseInt(it.toString(), 16))}
                .map {pad(it) + it}.joinToString("")

        private val pad: (String) -> String = { binary ->
            var pad = ""
            (binary.length..3).forEach {
                pad += 0
            }
            pad
        }
        private val fromBinary: (String) -> Int = {Integer.parseInt(it, 2)}
        private val longFromBinary: (String) -> Long = {it.toLong(2)}

        fun parseLiteral(binaryPacket: String): Pair<Long, String> {
            // skip the first six as we've already checked version and type
            // this may be a problem as we're not checking to stop at 0 first in the drop(1)
//            return fromBinary(binaryPacket.drop(6).windowed(5, 5, false).map {chunk -> chunk.drop(1)}.joinToString(""))
            var value = ""
            var counts = 0
            for (chunk in binaryPacket.drop(6).chunked(5)) {
                counts += 1
                value += chunk.drop(1)
                if (chunk[0] == '0') {
                    break
                }
            }
            println("there were $counts cunks scanned, so total string size is ${6 + (counts*5)}")
            return longFromBinary(value) to binaryPacket.drop(6+(counts*5))
        }

        fun extractLength(binaryString: String, count: Int): Int = fromBinary(binaryString.drop(7).take(count))
    }

    open class Packet(val version: Int, val type: Int )
    class Literal(version: Int, type: Int, val value: Long): Packet(version, type)
    class Operator(version: Int, type: Int): Packet(version, type) {
        val children: MutableList<Packet> = mutableListOf()
    }
}