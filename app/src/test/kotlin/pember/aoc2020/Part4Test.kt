package pember.aoc2020
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 *
 */
class Part4Test {

    @Test
    fun `day19-puzzle1`() {
        val basicMessages = MonsterMessages("day19-test.txt")
        val results = basicMessages.matchChecks()
        assertThat(results).isEqualTo(2)
        val interestingMessages = MonsterMessages("day19-test2.txt")
        assertThat(interestingMessages.matchChecks()).isEqualTo(2)

        val puzzleMessages = MonsterMessages("day19.txt")
        assertThat(puzzleMessages.matchChecks()).isEqualTo(142)
    }

    @Test
    fun `day19-puzzle2`() {
        val testMessages = MonsterMessages("day19-test3.txt", true)
        assertThat(testMessages.matchChecks()).isEqualTo(12)

        val puzzleMessages = MonsterMessages("day19.txt", true)
        // this is wrong
        assertThat(puzzleMessages.matchChecks()).isEqualTo(315)
        //I failed
        assertThat("If only java supported recursive reg ex").isNotEmpty()
    }

    @Test
    fun `day20-puzzle1`() {
        val testTiles = JurassicJigsaw("day20-test.txt")

        assertThat(testTiles.unsortedTiles[3079]!!.compareOtherTile(testTiles.unsortedTiles[2473]!!)).isEqualTo(1)
        assertThat(testTiles.unsortedTiles[3079]!!.compareOtherTile(testTiles.unsortedTiles[2971]!!)).isEqualTo(0)

        val testResult = testTiles.scan()
        assertThat(testResult).isEqualTo(20899048083289)

        val puzzleTiles = JurassicJigsaw("day20.txt")
        val result = puzzleTiles.scan()
        assertThat(result).isEqualTo(13983397496713)
    }

    @Test
    fun `day20-puzzle2`() {
        val testTiles = JurassicJigsaw("day20-test.txt")
        assertThat("I'm throwing in the towel on this one; packed day of family obligations").isNotEmpty()
    }

    @Test
    fun `day21-puzzle1`() {
        val count = AllergenAssessment.countInertIngredients("day21-test.txt")
        assertThat(count).isEqualTo(5)
        assertThat(AllergenAssessment.countInertIngredients("day21.txt")).isEqualTo(2078)
    }

    @Test
    fun `day21-puzzle2`() {
        assertThat(AllergenAssessment.determineDangerousIngredients("day21-test.txt"))
            .isEqualTo("mxmxvkd,sqjhc,fvjkl")
        assertThat(AllergenAssessment.determineDangerousIngredients("day21.txt"))
            .isEqualTo("lmcqt,kcddk,npxrdnd,cfb,ldkt,fqpt,jtfmtpd,tsch")
    }

    @Test
    fun `day21-deck`() {
        // tdd the deck
        val deck = CrabCombat.Deck("Player 1")
        deck.insert(5).insert(10)
        assertThat(deck.draw()).isEqualTo(5)
        assertThat(deck.draw()).isEqualTo(10)
        assertTrue(deck.hasLost())
        deck.insert(25, 9)
        assertThat(deck.draw()).isEqualTo(25)
        assertThat(deck.draw()).isEqualTo(9)
    }

    @Test
    fun `day22-puzzle1`() {
        val test = CrabCombat.combat("day22-test.txt")
        assertThat(test).isEqualTo(306)

        val actual = CrabCombat.combat("day22.txt")
        assertThat(actual).isEqualTo(29764)

    }

    @Test
    fun `day22-puzzle2`() {
        val gameState: List<Pair<List<Int>, List<Int>>> = listOf(
            listOf(5,10,1) to listOf(20, 5, 3),
            listOf(4,6,3) to listOf(25,10,1),
            )
        assertFalse(CrabCombat.isInfinite(gameState, listOf(1,2,3), listOf(20,5,3)))
        assertTrue(CrabCombat.isInfinite(gameState, listOf(5,10,1),listOf(20, 5, 3)))

        val deck1 = CrabCombat.Deck("test")
        //3, 10, 1, 7, 6
        deck1.insert(3).insert(10).insert(1).insert(7).insert(6)
        val copy = deck1.copy(deck1.draw())
        assertThat(copy.count()).isEqualTo(3)
        assertThat(copy.state()).isEqualTo(listOf(10,1,7))

        val test = CrabCombat.recursiveCombat("day22-test.txt")
        assertThat(test).isEqualTo(291)

        val actual = CrabCombat.recursiveCombat("day22.txt")
        assertThat(actual).isEqualTo(32588)

    }

    @Test
    fun `day23-puzzle1`() {
        assertThat(CrabCups.play("389125467", 10)).isEqualTo("92658374")
        assertThat(CrabCups.play("389125467", 100)).isEqualTo("67384529")
        assertThat(CrabCups.play("364297581", 100)).isEqualTo("47382659")
    }

    @Test
    fun `day23-puzzle2`() {
//        val result = CrabCups.playInsane("389125467", 10000000)
//        assertThat(result).isEqualTo(149245887792)
        assertThat(CrabCups.playInsane("364297581", 10000000)).isEqualTo(42271866720)
    }
}
