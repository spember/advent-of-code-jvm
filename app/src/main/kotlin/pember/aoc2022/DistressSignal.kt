package pember.aoc2022
import com.google.gson.Gson
import java.util.*

class DistressSignal(private val fileName: String): Aoc2022() {
    private val gson = Gson()
    private val pairs = reader.readLinesIntoGroups(fileName)
        .map {
            gson.fromJson(it.first(), List::class.java) to gson.fromJson(it.last(), List::class.java)
        }
        .toList()

    fun sumRightOrder(): Int {
        return pairs.mapIndexed { index, pair ->
            val result = compare(pair.first, pair.second)
            println("index ${index+1} is ${result}")
            if (result) {
                index + 1
            } else {
                0
            }
        }.sum()

    }

    fun compare(left: List<*>, right: List<*>): Boolean {
        if (left.isEmpty() || (left.isEmpty() && right.isEmpty())) {
            return true
        }
        if (right.isEmpty()) {
            return false
        }

        // zip and compare. for each, if one is a list, go deeper. If one is a list and the other is not
        // make the not list a list
        // two linked lists?
        val leftItems = LinkedList(left)
        val rightItems = LinkedList(right)

        while(leftItems.isNotEmpty() && rightItems.isNotEmpty()){
            var l = leftItems.poll()
            var r = rightItems.poll()
//            println("Comparing $l and $r")
            if (l is Double) {
               l = l.toInt()
            }
            if (r is Double) {
                r = r.toInt()
            }
            // if l is a double and r is a double -> compare
            if (l is Int && r is Int) {
                if (l > r) {
                    return false
                }
            } else if (l is List<*> && r is Int) {
                val z = compare(l, listOf(r))
//                return compare(l, listOf(r))
                if (!z) {
                    return false
                }
            } else if (l is Int && r is List<*>) {
                val z = compare(listOf(l), r)
                if (!z) {
                    return false
                }
            } else if (l is List<*> && r is List<*>) {
                return compare(l, r)
            } else {
                println("Unknown situation ${l} and ${r}")
            }
            // if l is a list and r is a double

            // if l is a double and r is a list
        }
        // check if items are left
        println("end.. with ${leftItems} and ${rightItems}")
        if (rightItems.isEmpty() && leftItems.isNotEmpty()) {
            return false
        }

        return true
    }
}