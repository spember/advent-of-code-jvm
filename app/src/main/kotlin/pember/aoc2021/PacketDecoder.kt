package pember.aoc2021

import pember.utils.YearlyFileReader
import java.lang.RuntimeException

class PacketDecoder {

    fun decodeFromFile(fileName:String): Pair<Packet, String> =
        decodeHex(YearlyFileReader(2021).readLines(fileName).first())

    fun decodeHex(hexString: String): Pair<Packet, String> = decode(hexToBinary(hexString))

    fun decode(binaryString: String): Pair<Packet, String> {
        val (version, type) = getVersionAndType(binaryString)
        return if (type == 4) {
            // is literal
            val (value, remainder) = parseLiteral(binaryString)
            Literal(version, type, value) to remainder
        } else {
            // it's an operator!
            val operator = Operator(version, type)
            if (binaryString.drop(6).take(1) == TOTAL_LENGTH_OPERATOR) {
                val totalBits = extractLength(binaryString, 15)
                // extract bits and iterate, add to children, return this operator + 'outer' remainder
                var subPacketBits: String = binaryString.drop(7+15).take(totalBits)
                while(subPacketBits.isNotEmpty()) {
                    val (child, innerRemainder) = decode(subPacketBits)
                    operator.children.add(child)
                    subPacketBits = innerRemainder
                }
                operator to binaryString.drop(7 + 15 +  totalBits)

            } else {
                val subPacketCount = extractLength(binaryString, 11)
                var innerRemainder = binaryString.drop(7+11)
                (1..subPacketCount).forEach {
                    val (child, remainder) = decode(innerRemainder)
                    operator.children.add(child)
                    innerRemainder = remainder
                }
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

    /**
     * calculates the expression of the Packet tree
     */
    fun process(packet: Packet): Long {
        return if (packet is Literal)  packet.value
        else {
            val childResults = (packet as Operator).children.map {process(it)}
            when((packet).type) {
                0 -> childResults.fold(0L) { acc, value -> acc + value}

                1 -> childResults.fold(1) { acc, value -> acc* value}

                2 -> childResults.minOrNull()!!

                3 -> childResults.maxOrNull()!!

                5 -> if (childResults.first() > childResults.last()) {
                        1
                    } else {
                        0
                    }

                6 -> if (childResults.first() < childResults.last()) {
                        1
                    } else {
                        0
                    }

                7 -> if (childResults.first() == childResults.last()) {
                        1
                    } else {
                        0
                    }
                else -> {
                    throw RuntimeException()
                }
            }
        }
    }

    companion object {
        private const val TOTAL_LENGTH_OPERATOR = "0"
        private const val HEADER_BITS = 6
        private const val LITERAL_PACKET_SIZE = 5

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

            var value = ""
            var counts = 0
            for (chunk in binaryPacket.drop(HEADER_BITS).chunked(LITERAL_PACKET_SIZE)) {
                counts += 1
                value += chunk.drop(1)
                if (chunk[0] == '0') {
                    break
                }
            }
            return longFromBinary(value) to binaryPacket.drop(HEADER_BITS+(counts*LITERAL_PACKET_SIZE))
        }

        fun extractLength(binaryString: String, count: Int): Int = fromBinary(binaryString.drop(HEADER_BITS+1).take(count))
    }

    open class Packet(val version: Int, val type: Int )
    class Literal(version: Int, type: Int, val value: Long): Packet(version, type)
    class Operator(version: Int, type: Int): Packet(version, type) {
        val children: MutableList<Packet> = mutableListOf()
    }
}