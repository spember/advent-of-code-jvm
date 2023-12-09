package pember.aoc2023

object HauntedWasteland: Aoc2023(8) {

    fun part1() {
        val p1Test = countTravel("day$day-test1.txt")
        println("p1Test = Took ${p1Test}")
        assert(p1Test == 2)

        val p1Test2 = countTravel("day$day-test2.txt")
        println("p2Test = Took ${p1Test2}")
        assert(p1Test == 6)

        val p1 = countTravel("day$day.txt")
        println("p2Test = Took ${p1}")
        assert(p1 == 11567)
    }

    fun part2() {
        println("****** part 2")
        val p2Test = ghostTravel("day$day-test3.txt")
        println("p2 Test = ${p2Test}")
        assert(p2Test == 6L)

        val p2 = ghostTravel("day$day.txt")
        println("p2 = ${p2}")
    }



    private fun countTravel(fn: String): Int {
        val (dirs, nodes) = parseFile(fn)

        // travel!
        var counter = 0
        var rl = 0
        var currentNode = "AAA"
        while(currentNode != "ZZZ") {
            currentNode = selectNode(nodes[currentNode]!!, dirs[rl])
            counter++
            rl++
            if (rl >= dirs.size) {
                rl = 0
            }
            if (counter % 25 == 0) {
                println("steps travelled = ${counter}")
            }
        }
        return counter
    }

    private fun ghostTravel(fn: String): Long {
        val (dirs, nodes) = parseFile(fn)
        var ghosts = nodes.keys.filter { it.endsWith("A") }.toMutableList()

        println("A nodes = ${ghosts.size}, ghosts starting at ${ghosts}")
        // find the steps for each ghost to reach the first Z
        val stepCounts = mutableListOf<List<Int>>()
//        println(nodes)
        ghosts.forEach { startPosition ->
            var counter = 0
            var currentNode: String = startPosition
            var firstZ: String = ""
            val ghostCycles = mutableListOf<Int>()
//            println("First step should be L -> ${dirs[counter % dirs.size]}")
            while(true) {
                while(counter == 0  || !currentNode.endsWith("Z")){
                    currentNode = selectNode(nodes[currentNode]!!, dirs[counter % dirs.size])

                    counter++
                }
                ghostCycles.add(counter)
                // found a z, figure out if we've seen a z yet
                if (firstZ.isEmpty()) {
                    firstZ = currentNode
                    counter = 0
                } else if(currentNode == firstZ) {
                    break
                }
            }
            stepCounts.add(ghostCycles)
        }

        stepCounts.forEach { println(it) }
        // all of the steps were the same, for each one of the z's
        val steps = stepCounts.map { it.first().toLong() }
        return lcm(steps)
    }

    private fun parseFile(file: String): Pair<CharArray, Map<String, Pair<String,String>>> {
        val seq = reader.readLines(file)
        val dirs = seq.take(1).first().toCharArray()

        val nodes: Map<String, Pair<String, String>> = seq.drop(2)
            .map { convertNode(it) }
            .associate { it.first to it.second  }
        return dirs to nodes
    }

    private fun convertNode(line: String): Pair<String, Pair<String, String>> {
        val top = line.split(" = ")
        val right = top[1].substring(1, top[1].length-1).split(", ")
        return top[0] to (right[0] to right[1])
    }

    private fun selectNode(option: Pair<String, String>, dir: Char): String {
        return if (dir == 'L') {
            option.first
        } else {
            option.second
        }
    }

    /*
    Needed a hint on this one.
    LCM algorithm from https://www.geeksforgeeks.org/lcm-of-given-array-elements/
     */
    private fun greatestCommon(a: Long, b: Long): Long {
        if (b == 0L) {
            return a
        }
        return greatestCommon(b, a % b)
    }

    private fun lcm(numbers: List<Long>): Long {
        return numbers.fold(1L) {l,r->
            (l * r)/ greatestCommon(l,r)
        }
    }
}