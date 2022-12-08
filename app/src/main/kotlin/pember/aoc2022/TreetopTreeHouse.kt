package pember.aoc2022

class TreetopTreeHouse(private val fileName: String): Aoc2022() {
    fun countVisible(): Int {
        val treeMap = prepareMap()
        for (r in 1 until treeMap.size-1) {
            for (c in 1 until treeMap[r].size-1) {
                if (calculateVisibleInLane(treeMap[r], c)) {
                    treeMap[r][c].isVisible = true
                }
                if (calculateVisibleInLane(treeMap.map { row -> row[c] }, r)) {
                    treeMap[r][c].isVisible = true
                }
            }
        }
        return countVisible(treeMap, true)
    }

    fun findMaxScenic(): Int {
        val treeMap = prepareMap()
        var maxScenic: Tree? = null
        for (r in 1 until treeMap.size-1) {
            for (c in 1 until treeMap[r].size-1) {
                treeMap[r][c].scenicScore = calculateScenicScore(treeMap[r], c) *
                        calculateScenicScore(treeMap.map { row -> row[c] }, r)
                if(maxScenic == null  || treeMap[r][c].scenicScore > maxScenic.scenicScore) {
                    maxScenic = treeMap[r][c]
                }
            }
        }
        return maxScenic!!.scenicScore
    }

    private fun calculateVisibleInLane(lane: List<Tree>, currentPos: Int): Boolean {
        // for each tree in the line, see how many are taller or equal. if results are empty, then the tree is not visible
        // if all left or all right are less than target, target is visible
        val left = lane.subList(0, currentPos)
        val right = lane.subList(currentPos+1, lane.size)
        val target = lane[currentPos]
        if (left.all {it.height < target.height}) {
            return true
        }
        if (right.all {it.height < target.height}) {
            return true
        }
        return false
    }

    private fun calculateScenicScore(lane: List<Tree>, currentPos: Int): Int =
        countRange((currentPos-1 downTo 0).toList(), lane, currentPos) *
        countRange((currentPos+1 until lane.size).toList(), lane, currentPos)

    private fun countRange(range: List<Int>, lane: List<Tree>, currentPos: Int): Int {
        var s = 0
        for (i in range){
            s++
            if (lane[i].height >= lane[currentPos].height) {
                break
            }
        }
        return s
    }

    private fun countVisible(treeMap: List<List<Tree>>, printTrees: Boolean): Int {
        if (printTrees) {
            treeMap.map { it.joinToString(" ")}
                .forEach { println(it) }
        }
        return treeMap.flatMap { row -> row.map { it.isVisible } }.count { it }
    }

    private fun prepareMap(): List<List<Tree>> {
        val map = reader.readNonEmptyLines(fileName)
            .map { line -> line.toCharArray() // look at all these beautiful casts
                .toList()
                .map { it
                    .toString()
                    .toInt()}
            }
            .map {space-> space. map { Tree(it, false) }}
            .toList()
        // set edges to visible
        map.first().forEach {it.isVisible = true}
        map.last().forEach { it.isVisible = true }
        map.forEach { col ->
            col.first().isVisible = true
            col.last().isVisible = true
        }
        return map
    }

    private data class Tree(val height: Int, var isVisible: Boolean = false, var scenicScore: Int = 0) {
        override fun toString(): String {
            return "${height}${if (isVisible) '!' else '.'}"
        }
    }
}