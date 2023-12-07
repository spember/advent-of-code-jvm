package pember.utils

import java.lang.RuntimeException

/**
 * A circular list of unique values, allowing for O(1) retrieval of individual values in the circle
 */
open class CircularList<T>(seed: List<T>) {
    protected val index: MutableMap<T, Node<T>> = mutableMapOf()

    init {
        if (seed.isEmpty()) {
            throw RuntimeException("initial values must not be empty")
        }
        val first = Node(seed.first())
        index[first.value] = first
        first.next = first
        var previous = first.value
        seed.drop(1)
            .forEach {
                assert(insertAfter(it, previous))
                previous = it
            }
    }

    fun count(): Int {
        return index.size
    }

    fun getFrom(start: T): List<T> {
        val collected = mutableListOf<T>()
        var current = start
        for (i in 0 until index.size) {
            collected.add(index[current]!!.value)
            current = index[current]!!.next.value
        }
        return collected
    }

    fun getAfter(target: T) : T? = index[target]?.next?.value

    fun insertAfter(additionalValue: T, afterValue: T): Boolean {
        if (index.containsKey(additionalValue) || !index.containsKey(afterValue)) {
            return false
        }
        val targetNode = index[afterValue]!!
        val nextNode = targetNode.next
        val additionalNode = Node(additionalValue)
        index[additionalNode.value] = additionalNode
        targetNode.next = additionalNode
        additionalNode.next = nextNode
        return true
    }


    fun insertAfter(additionalValues: List<T>, afterValue: T): Int {
        if (additionalValues.isEmpty()) {
            return 0
        }
        var previous = afterValue
        return additionalValues.map {
            val result = insertAfter(it, previous)
            previous = it
            result
        }
            .filter { it }
            .count()
    }


    fun removeAfter(target: T, numberToRemove: Int): List<T> {
        var collectedItems = mutableListOf<T>()
        if (numberToRemove < 0 || numberToRemove >= index.size || !index.containsKey(target)) {
            return collectedItems
        }

        index[target]?.let { node ->
            for (i in 0 until numberToRemove) {
                collectedItems.add(node.next.value)
                node.next = node.next.next
                index.remove(collectedItems.last())
            }
        }
        return collectedItems
    }


    protected class Node<T>(val value: T) {
        lateinit var next: Node<T>
    }
}
