package pember.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertFalse
import org.junit.jupiter.api.Test

/**
 *
 */
class CircularListTest {

    @Test
    fun `Adding a single item`() {
        val circle = CircularList<Int>(listOf(2,5))
        assertThat(circle.getFrom(2)).isEqualTo(listOf(2,5))
        circle.insertAfter(10,2)
        assertThat(circle.getFrom(2)).isEqualTo(listOf(2,10,5))
    }

    @Test
    fun `Adding multiple items`() {
        val circle = CircularList(listOf("foo", "bar"))
        assertThat(circle.getFrom("bar")).isEqualTo(listOf("bar", "foo"))
        circle.insertAfter("baz","bar")
        circle.insertAfter("baz2","bar")
        assertThat(circle.getFrom("baz")).isEqualTo(listOf("baz", "foo", "bar", "baz2"))
    }

    @Test
    fun `Cannot add the same value twice`() {
        val circle = CircularList(listOf(1,2,3))
        assertFalse(circle.insertAfter(2, 1))
    }

    @Test
    fun `Adding a List`() {
        val circle = CircularList<Int>(listOf(2,5))
        val insertCount = circle.insertAfter(listOf(7,8,9), 2)
        assertThat(insertCount).isEqualTo(3)
        assertThat(circle.getFrom(2)).isEqualTo(listOf(2,7,8,9,5))
        assertThat(circle.getAfter(2)).isEqualTo(7)
    }

    @Test
    fun `Removing`() {
        val circle = CircularList(listOf(2,5))
        circle.removeAfter(5, 1)
        assertThat(circle.getFrom(5)).isEqualTo(listOf(5))

        circle.insertAfter(10, 5)
        circle.insertAfter(listOf(1,2,3), 5)
        assertThat(circle.getFrom(5)).isEqualTo(listOf(5,1,2,3,10))
        val removed = circle.removeAfter(10, 2)
        assertThat(removed).isEqualTo(listOf(5,1))
        assertThat(circle.getFrom(3)).isEqualTo(listOf(3,10,2))

    }
}
