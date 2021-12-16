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
        val (packet, remainder) = PacketDecoder("").decode("110100101111111000101000")
        assertThat(packet.version).isEqualTo(6)
        assertThat(packet.type).isEqualTo(4)
        assertThat(packet::class.java).isEqualTo(PacketDecoder.Literal::class.java)
        assertThat((packet as PacketDecoder.Literal).value).isEqualTo(2021)
        assertThat(remainder).isEqualTo("000")
    }

    @Test
    fun `Day16 - Decoding a length Operator`() {
        val (packet, remainder) = PacketDecoder("").decode(PacketDecoder.hexToBinary("38006F45291200"))
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
        val (packet, remainder) = PacketDecoder("").decode(PacketDecoder.hexToBinary("EE00D40C823060"))
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
        val (packet, _) = PacketDecoder("").decodeHex("8A004A801A8002F478")
        assertThat(packet.version).isEqualTo(4)
        val operator = packet as PacketDecoder.Operator
        assertThat(operator.children.size).isEqualTo(1)
        assertThat(operator.children.first().version).isEqualTo(1)
        assertThat(PacketDecoder("").sumVersion(packet)).isEqualTo(16)

        val (packet2, _) = PacketDecoder("").decodeHex("A0016C880162017C3686B18A3D4780")
        assertThat(PacketDecoder("").sumVersion(packet2)).isEqualTo(31)

        val (puzzlePacket, _) = PacketDecoder("").decodeHex("820D4100A1000085C6E8331F8401D8E106E1680021708630C50200A3BC01495B99CF6852726A88014DC9DBB30798409BBDF5A4D97F5326F050C02F9D2A971D9B539E0C93323004B4012960E9A5B98600005DA7F11AFBB55D96AFFBE1E20041A64A24D80C01E9D298AF0E22A98027800BD4EE3782C91399FA92901936E0060016B82007B0143C2005280146005300F7840385380146006900A72802469007B0001961801E60053002B2400564FFCE25FEFE40266CA79128037500042626C578CE00085C718BD1F08023396BA46001BF3C870C58039587F3DE52929DFD9F07C9731CC601D803779CCC882767E668DB255D154F553C804A0A00DD40010B87D0D6378002191BE11C6A914F1007C8010F8B1122239803B04A0946630062234308D44016CCEEA449600AC9844A733D3C700627EA391EE76F9B4B5DA649480357D005E622493112292D6F1DF60665EDADD212CF8E1003C29193E4E21C9CF507B910991E5A171D50092621B279D96F572A94911C1D200FA68024596EFA517696EFA51729C9FB6C64019250034F3F69DD165A8E33F7F919802FE009880331F215C4A1007A20C668712B685900804ABC00D50401C89715A3B00021516E164409CE39380272B0E14CB1D9004632E75C00834DB64DB4980292D3918D0034F3D90C958EECD8400414A11900403307534B524093EBCA00BCCD1B26AA52000FB4B6C62771CDF668E200CC20949D8AE2790051133B2ED005E2CC953FE1C3004EC0139ED46DBB9AC9C2655038C01399D59A3801F79EADAD878969D8318008491375003A324C5A59C7D68402E9B65994391A6BCC73A5F2FEABD8804322D90B25F3F4088F33E96D74C0139CF6006C0159BEF8EA6FBE3A9CEC337B859802B2AC9A0084C9DCC9ECD67DD793004E669FA2DE006EC00085C558C5134001088E308A20")
        println("wooo")
        assertThat(PacketDecoder("").sumVersion(puzzlePacket)).isEqualTo(100)
    }
}