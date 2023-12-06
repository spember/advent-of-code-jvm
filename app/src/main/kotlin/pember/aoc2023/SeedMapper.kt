package pember.aoc2023

import java.lang.Long.*
import java.util.LinkedList

object SeedMapper: Aoc2023() {

    fun findLowLocation(fn: String): Long {
        val groups = reader.readLinesIntoGroups(fn)
        val seeds = groups.take(1).first().map {
            it.split(":").last().trim().split(" ").map { parseLong(it) }
        }.first()


        val mappers: Map<String, SourceToDestinationMap> = groups.drop(1).map {
            SourceToDestinationMap(it.first(), it.drop(1))
        }.map { it.source to it }
            .toMap()
//        val soilMapper = mappers.get("seed")
//        seeds.forEach {id ->
//            println("seed $id -> ${soilMapper!!.getDestination(id)}.. next lookup is ${soilMapper.destination}")
//        }

        // keep going with each seed until we get to there's no more in the map
        val lowest = seeds.map {seedId ->
            var mapper = mappers.get("seed")
            var sourceId = seedId
            while(mapper != null) {
//                println("searching for ${sourceId} + ${mapper.destination}")
                sourceId = mapper.getDestination(sourceId)
                mapper = mappers.get(mapper.destination)
            }
            sourceId
        }
            .minOrNull()

        return lowest!!
    }

    fun seedRanges(fn: String): Long {
        val groups = reader.readLinesIntoGroups(fn)
        val seeds = groups.take(1).first().map {
            it.split(":").last().trim().split(" ").map { parseLong(it) }
        }.first()


        val mappers: Map<String, SourceToDestinationMap> = groups.drop(1).map {
            SourceToDestinationMap(it.first(), it.drop(1))
        }.map { it.source to it }
            .toMap()

        val segmentQueue = LinkedList<Pair<String,Pair<Long, Long>>>()


        // start with location ids and walk backwards
        seeds.windowed(2, 2, false) {
            segmentQueue.add("seed" to (it[0] to it[1]))
            val mapper = mappers.get("seed")!!
            println(mapper.getDestinationRages(it[0], it[1]))

        }
        return 46
//
//        var counter = 0
//        val locations = mutableSetOf<Long>()
//        while(segmentQueue.isNotEmpty()) {
//            val segment = segmentQueue.poll()
//            val mapper = mappers[segment.first]
//            if (mapper == null) {
//                locations.add(segment.second.first)
//
//            } else {
//                val results = mapper.getDestinationRages(segment.second.first, segment.second.second)
//                println("adding results ${results}")
//                segmentQueue.addAll(results)
//            counter++
//            if (counter > 4000 ) {
//                println("hit!")
//                break
//            }
//            }
//
//
//        }
//        println("locations is ${locations}")
//        return locations.minOrNull()!!
    }
    
    private class SourceToDestinationMap(val name: String, data: List<String>) {
        var source: String
        var destination: String
        val ranges: MutableList<Array<Long>> = mutableListOf()
        init {
            val parts = name.trim().replace(" map:", "").split("-to-")
            source = parts[0]
            destination = parts[1]

            //each 'data' item contains destination to source mapping, so confusing
            // in each part, the 3rd bit is the range. the first is the destination, the second is the range.
            // so 50, 98, 2
            // seed 98 -> 50, 99 -> 51
            // so position in the toDestination maps to ints
            // if id is >= a sourceStart && < sourceStart + range -> return destPart + id-sourceStart
            data.forEach {populateData(it)}
        }

        private fun populateData(inputLine: String) {
            val fields = inputLine.trim().split(" ").map { parseLong(it) }
            val (destinationStart, sourceStart, range) = fields
            ranges.add(arrayOf(destinationStart, sourceStart, range))
        }

        fun getDestination(sourceId: Long): Long {
            // if id is >= a sourceStart && < sourceStart + range -> return destPart + id-sourceStart
            ranges.forEach { arr ->
                if (sourceId >= arr[1] && sourceId < arr[1] + arr[2]) {
                    return arr[0] + (sourceId-arr[1])
                }
            }
            return sourceId
        }

        fun getDestinationRages(sourceId: Long, sourceRange: Long) : List<Pair<String, Pair<Long, Long>>> {
            // for each source Id and range ... scan our ranges.

            val destinationRanges = mutableListOf<Pair<String, Pair<Long, Long>>>()
            val overlaps = ranges.filter { overlaps(sourceId, sourceId + sourceRange, it[1], it[1]+it[2]) }
            println (" source is $sourceId + $sourceRange -> ${sourceId+sourceRange} ")
            if (overlaps.isEmpty()) {
                // pass through the source ids to the next destination
                println("no overlaps!")
                return listOf(this.destination to (sourceId to sourceRange))
            }
            // this algorithm is vastly incorrect
            // otherwise...
            println("overlaps ${overlaps.size} -> ${overlaps.map{it.toList()}}")
            overlaps.forEach {
                val endRange = it[1]+it[2]
                val endSource = sourceId + sourceRange
                if (sourceId < it[1]) {
                    println("adding pre")
                    destinationRanges.add(
                        source to (sourceId to (it[1]-sourceId))
                    )
                }

                if ((endSource) > endRange) {
                    println("adding post")
                        destinationRanges.add(
                            source to (endRange to (endSource - endRange))
                        )
                    }
                destinationRanges.add(
                        destination to ((it[0] + max(0L, sourceId-it[1])) to sourceRange)
                    )

            }

            return destinationRanges
        }

        private fun overlaps(startA: Long, endA: Long, startB:Long, endB: Long): Boolean {
            return max(startA, startB) <= min(endA, endB)
        }

    }
}