package pember.aoc2021

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Part2Test {

    @Test
    fun `Day9 - Puzzle1`() {
        assertThat(SmokeBasin("day9-test.txt").lowPointRisk()).isEqualTo(15)
        assertThat(SmokeBasin("day9.txt").lowPointRisk()).isEqualTo(465)
    }

    @Test
    fun `Day9 - Puzzle2`() {
        assertThat(SmokeBasin("day9-test.txt").largestBasins(3)).isEqualTo(1134)
        assertThat(SmokeBasin("day9.txt").largestBasins(3)).isEqualTo(1269555)
    }

    @Test
    fun `Day10 - Puzzle1`() {
        assertThat(SyntaxScoring("day10-test.txt").findFirst()).isEqualTo(26397)
        assertThat(SyntaxScoring("day10.txt").findFirst()).isEqualTo(240123)
    }

    @Test
    fun `Day10 - Puzzle2`() {
        assertThat(SyntaxScoring("day10-test.txt").findMiddleScoreOfCompleted()).isEqualTo(288957)
        assertThat(SyntaxScoring("day10.txt").findMiddleScoreOfCompleted()).isEqualTo(3260812321)
    }

    @Test
    fun `Day11 - Puzzle1`() {
//        assertThat(DumboOctopus("day11-pretest.txt").countFlashes(2)).isEqualTo(20)
        assertThat(DumboOctopus("day11-test.txt").countFlashes(10)).isEqualTo(204)
        assertThat(DumboOctopus("day11-test.txt").countFlashes(100)).isEqualTo(1656)
        assertThat(DumboOctopus("day11.txt").countFlashes(100)).isEqualTo(1713)
    }
    @Test
    fun `Day11 - Puzzle2`() {
//        assertThat(DumboOctopus("day11-pretest.txt").countFlashes(2)).isEqualTo(20)
        assertThat(DumboOctopus("day11-test.txt").findAll()).isEqualTo(195)
        assertThat(DumboOctopus("day11.txt").findAll()).isEqualTo(502)

    }

    @Test
    fun `Day12 - Puzzle1`() {
        assertThat(PassagePathing.Cave("start")).isEqualTo(PassagePathing.Cave("start"))
        assertThat(PassagePathing.Cave("start")).isNotEqualTo(PassagePathing.Cave("end"))
        assertThat(PassagePathing("day12-test1.txt").countPaths()).isEqualTo(10)
        assertThat(PassagePathing("day12-test2.txt").countPaths()).isEqualTo(19)
        assertThat(PassagePathing("day12-test3.txt").countPaths()).isEqualTo(226)
        assertThat(PassagePathing("day12.txt").countPaths()).isEqualTo(5457)
    }

    @Test
    fun `Day12 - Puzzle2`() {
        assertThat(PassagePathing("day12-test1.txt").countPaths(true)).isEqualTo(36)
        assertThat(PassagePathing("day12-test2.txt").countPaths(true)).isEqualTo(103)
        assertThat(PassagePathing("day12-test3.txt").countPaths(true)).isEqualTo(3509)
        assertThat(PassagePathing("day12.txt").countPaths(true)).isEqualTo(128506)
    }

    @Test
    fun `Day13 - Puzzle1`() {
        assertThat(TransparentOrigami("day13-test.txt").foldToInstructions()).isEqualTo(17)
        assertThat(TransparentOrigami("day13.txt").foldToInstructions()).isEqualTo(842)
    }

    @Test
    fun `Day13 - Puzzle2`() {
        assertThat(TransparentOrigami("day13-test.txt").foldToInstructions(false)).isEqualTo(16)
        assertThat(TransparentOrigami("day13.txt").foldToInstructions(false)).isEqualTo(95)
    }

    @Test
    fun `Day14 - Puzzle1`() {
        assertThat(ExtendedPolymerization("day14-test.txt").polymerElements(10)).isEqualTo(1588)
        assertThat(ExtendedPolymerization("day14.txt").polymerElements(10)).isEqualTo(4517)
    }

    @Test
    fun `Day14 - Puzzle2`() {
        assertThat(ExtendedPolymerization("day14-test.txt").polymerElements(40)).isEqualTo(2188189693529)
        assertThat(ExtendedPolymerization("day14.txt").polymerElements(40)).isEqualTo(4704817645083)
    }

    @Test
    fun `Day15 - Puzzle1`() {
        assertThat(Chiton("day15-test.txt").findLowestRisk()).isEqualTo(40)
        assertThat(Chiton("day15.txt").findLowestRisk()).isEqualTo(503)
    }

    @Test
    fun `Day15 - Puzzle2`() {
        assertThat(Chiton("day15-test.txt").expandedRisk()).isEqualTo(315)
        assertThat(Chiton("day15.txt").expandedRisk()).isEqualTo(2853)
    }

    @Test
    fun `Day16 - Decoding a Literal`() {
        val (packet, remainder) = PacketDecoder().decode("110100101111111000101000")
        assertThat(packet.version).isEqualTo(6)
        assertThat(packet.type).isEqualTo(4)
        assertThat(packet::class.java).isEqualTo(PacketDecoder.Literal::class.java)
        assertThat((packet as PacketDecoder.Literal).value).isEqualTo(2021)
        assertThat(remainder).isEqualTo("000")
    }

    @Test
    fun `Day16 - Decoding a length Operator`() {
        val (packet, remainder) = PacketDecoder().decode(PacketDecoder.hexToBinary("38006F45291200"))
        assertThat(packet.version).isEqualTo(1)
        assertThat(packet.type).isEqualTo(6)
        assertThat(remainder).isEqualTo("0000000")
        val operator = packet as PacketDecoder.Operator

        assertThat(operator.children.size).isEqualTo(2)
        assertThat((operator.children.first() as PacketDecoder.Literal).value).isEqualTo(10)
        assertThat((operator.children.last() as PacketDecoder.Literal).value).isEqualTo(20)
    }

    @Test
    fun `Day16 - Deconding a count Operator`() {
        val (packet, remainder) = PacketDecoder().decode(PacketDecoder.hexToBinary("EE00D40C823060"))
        assertThat(packet.version).isEqualTo(7)
        assertThat(packet.type).isEqualTo(3)
        assertThat(remainder).isEqualTo("00000")
        val operator = packet as PacketDecoder.Operator
        assertThat(operator.children.size).isEqualTo(3)
        assertThat((operator.children.first() as PacketDecoder.Literal).value).isEqualTo(1)
        assertThat((operator.children[1] as PacketDecoder.Literal).value).isEqualTo(2)
        assertThat((operator.children.last() as PacketDecoder.Literal).value).isEqualTo(3)
    }

    @Test
    fun `Day16 - Puzzle1`() {
        val (packet, _) = PacketDecoder().decodeHex("8A004A801A8002F478")
        assertThat(packet.version).isEqualTo(4)
        val operator = packet as PacketDecoder.Operator
        assertThat(operator.children.size).isEqualTo(1)
        assertThat(operator.children.first().version).isEqualTo(1)
        assertThat(PacketDecoder().sumVersion(packet)).isEqualTo(16)

        val (packet2, _) = PacketDecoder().decodeHex("A0016C880162017C3686B18A3D4780")
        assertThat(PacketDecoder().sumVersion(packet2)).isEqualTo(31)

        val (puzzlePacket, _) = PacketDecoder().decodeFromFile("day16.txt")
        assertThat(PacketDecoder().sumVersion(puzzlePacket)).isEqualTo(938)
    }

    @Test
    fun `Day16 - Puzzle2`() {
        val pd = PacketDecoder()
        val (p1, _) = pd.decodeHex("C200B40A82")
        assertThat(pd.process(p1)).isEqualTo(3)
        assertThat(pd.process(pd.decodeHex("04005AC33890").first)).isEqualTo(54)
        assertThat(pd.process(pd.decodeHex("880086C3E88112").first)).isEqualTo(7)

        assertThat(pd.process(pd.decodeHex("9C0141080250320F1802104A08").first)).isEqualTo(1)

        assertThat(pd.process(pd.decodeFromFile("day16.txt").first)).isEqualTo(1495959086337)
    }
}