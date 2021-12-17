package pember.aoc2021

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Part3Test {

    @Test
    fun `Day17 - Puzzle1`() {
        assertThat(TrickShot.hitsY(Pair(20 to 30, -10 to -5))).isEqualTo(45)

        //target area: x=236..262, y=-78..-58
        assertThat(TrickShot.hitsY(Pair(236 to 262, -78 to -58))).isEqualTo(3003)
    }
}