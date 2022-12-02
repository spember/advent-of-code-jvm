package pember.aoc2022

class CalorieCounting(private val fileName:String): Aoc2022() {


    fun findMaxCal(): Int = sortCals().first()

    fun findTop3Cals(): Int = sortCals().take(3).sum()

    private fun sortCals(): Sequence<Int> = reader.readLinesIntoGroups(fileName).map { items ->
        val row = items.map {it.toInt()}
        row.sum()
    }
            .sortedDescending()


}