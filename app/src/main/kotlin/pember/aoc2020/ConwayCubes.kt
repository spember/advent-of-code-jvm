package pember.aoc2020

import pember.utils.FileReader

class ConwayCubes(fileInput: String) {

    private var grid: List<List<List<Cell>>>

    init {
        grid = createInitialGrid(fileInput)
        println("Grid dimensions are ${grid.size} x ${grid[0].size} x ${grid[0][0].size}")
    }

    private fun createInitialGrid(fileInput: String): List<List<List<Cell>>> {
        val slice = FileReader.readNonEmptyLines(fileInput)
            .map { line -> line.toCharArray().map {Cell(it)} }
            .toList()
        return listOf(slice)

    }



    fun countActiveCubes(): Int {
        return grid.map { list ->
            list.map { chars: List<Cell> -> chars.count { it.isActive()} }
                .sum()
        }.sum()
    }

    fun runCycle(){
        // generate next 'stage'
        // for each row, add an in-active point at the front and back
        // add a row of inactive at the top and bottom
        // add a grid at z-1, z-2
        expandGrid()
        // for each point vote the pending
        println("grid dimensions are ${grid.size}, ${grid[0].size}, ${grid[0][0].size}")
        for (z in grid.indices) {
            for (y in grid[z].indices) {
                for (x in grid[z][y].indices) {
                    grid[z][y][x].update(countActiveNeighbors(ConwayPoint(z,y,x)))
                }
            }
        }
        grid.forEach {
            it.forEach {
                it.forEach { it.transition()}
            }
        }
    }

    fun expandGrid() {
        // generate next 'stage'
        // for each row, add an in-active point at the front and back
        // add a row of inactive at the top and bottom
        // add a grid at z-1, z-2
        val newGrid: MutableList<List<List<Cell>>> = mutableListOf()
        val sliceSize = grid[0].size +2
        val negativeSlice = mutableListOf<List<Cell>>()
        for (n in 0..sliceSize-1) {
            negativeSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toMutableList())
        }
        newGrid.add(negativeSlice)
        for (i in grid.indices) {
            newGrid.add(expandSlice(i))
        }
        val positiveSlice = mutableListOf<List<Cell>>()
        for (n in 0..sliceSize-1) {
            positiveSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toList())
        }
        newGrid.add(positiveSlice)

        grid = newGrid
    }

    fun expandSlice(z: Int): List<List<Cell>> {
        val newSlice = mutableListOf<List<Cell>>()
        val sliceSize = grid[0].size +2
        newSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toList())
        newSlice.addAll(grid[z].map { cells ->
            val newChars = Array(sliceSize) {Cell(INACTIVE)}
            cells.forEachIndexed { index, c -> newChars[index+1] = c  }
            newChars.toList()
        })
        newSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toList())
        return newSlice
    }

    fun printZSlice(z: Int) {
        grid[z].forEach {
            it.forEach { print(it) }
            println("")
        }
    }
    fun printSlices() {
        for (z in grid.indices) {
            println("Slice at ${z}")
            printZSlice(z)
        }
    }

    fun countActiveNeighbors(origin: ConwayPoint): Int =
        generatePoints(origin)
            .filter { it.isInGrid(grid.size, grid[0].size, grid[0][0].size)}
            .filter {
                grid[it.x][it.y][it.z].isActive()
            }
            .count()

    private fun generatePoints(origin: ConwayPoint): List<ConwayPoint> {
        val points = mutableListOf<ConwayPoint>()
        for (xrange in -1..1) {
            for (yrange in -1..1) {
                for(zrange in -1..1) {
                        points.add(ConwayPoint(origin.x+xrange, origin.y + yrange, origin.z+zrange))
                }
            }
        }

        return points.filter {it != origin}
    }

    data class ConwayPoint(val x: Int, val y: Int, val z: Int) {
        fun isInGrid(xs: Int, ys:Int, zs:Int): Boolean {
            if (x == -1 || y == -1 || z==-1){
                return false
            } else return !(x >= xs || y >= ys || z >= zs)
        }
    }
}

class ConwayHyperCubes(fileInput: String) {

    private var grid: List<List<List<List<Cell>>>>

    init {
        grid = createInitialGrid(fileInput)
        println("Grid dimensions are ${grid.size} x ${grid[0].size} x ${grid[0][0].size} x ${grid[0][0][0].size}")
    }

    private fun createInitialGrid(fileInput: String): List<List<List<List<Cell>>>> {
        val slice = FileReader.readNonEmptyLines(fileInput)
            .map { line -> line.toCharArray().map {Cell(it)} }
            .toList()
        return listOf(listOf(slice))

    }



    fun countActiveCubes(): Int {
        return grid.map {
            it.map { list ->
                list.map { chars: List<Cell> ->
                    chars.count { it.isActive()} }.sum()
            }
            .sum()
        }.sum()
    }

    fun runCycle(){
        // generate next 'stage'
        // for each row, add an in-active point at the front and back
        // add a row of inactive at the top and bottom
        // add a grid at z-1, z-2
        expandHyper()
//        printSlices()
        // for each point vote the pending
        println("grid dimensions are ${grid.size}, ${grid[0].size}, ${grid[0][0].size}, ${grid[0][0][0].size}")
        for (w in grid.indices) {
            for (z in grid[w].indices) {
                for (y in grid[w][z].indices) {
                    for (x in grid[w][z][y].indices) {
                        grid[w][z][y][x].update(countActiveNeighbors(HyperPoint(w, z, y, x)))
                    }
                }
            }
        }
        grid.forEach {
            it.forEach {
                it.forEach {
                    it.forEach { it.transition()}
                }
            }
        }
    }

    fun expandHyper() {
        val newHyper: MutableList<List<List<List<Cell>>>> = mutableListOf()
        val cubeSize = grid[0].size +2
        val sliceSize = grid[0][0].size +2

        // empty cube on the ends
        val negCube = mutableListOf<List<List<Cell>>>()
        for (h in 0 until cubeSize) {
            val inner = mutableListOf<List<Cell>>()
            for (i in 0 until sliceSize) {
                inner.add(Array(sliceSize) {Cell(INACTIVE)}.toList())
            }
            negCube.add(inner)
        }
        newHyper.add(negCube)

        for (i in grid.indices) {
            newHyper.add(expandGrid(i))
        }

        val posCube = mutableListOf<List<List<Cell>>>()
        for (h in 0 until cubeSize) {
            val inner = mutableListOf<List<Cell>>()
            for (i in 0 until sliceSize) {
                inner.add(Array(sliceSize) {Cell(INACTIVE)}.toList())
            }
            posCube.add(inner)
        }
        newHyper.add(posCube)
        grid = newHyper
    }

    fun expandGrid(w:Int): List<List<List<Cell>>> {
        // generate next 'stage'



        val newGrid: MutableList<List<List<Cell>>> = mutableListOf()
        val sliceSize = grid[w][0].size +2
        val negativeSlice = mutableListOf<List<Cell>>()
        for (n in 0 until sliceSize) {
            negativeSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toMutableList())
        }
        newGrid.add(negativeSlice)

        for (i in grid[w].indices) {
            newGrid.add(expandSlice(w,i))
        }
        val positiveSlice = mutableListOf<List<Cell>>()
        for (n in 0 until sliceSize) {
            positiveSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toList())
        }
        newGrid.add(positiveSlice)

        return newGrid
    }

    fun expandSlice(w:Int, z: Int): List<List<Cell>> {
        val newSlice = mutableListOf<List<Cell>>()
        val sliceSize = grid[0][0].size +2
        newSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toList())
        newSlice.addAll(grid[w][z].map { cells ->
            val newChars = Array(sliceSize) {Cell(INACTIVE)}
            cells.forEachIndexed { index, c -> newChars[index+1] = c  }
            newChars.toList()
        })
        newSlice.add(Array(sliceSize) {Cell(INACTIVE)} .toList())
        return newSlice
    }

    fun countActiveNeighbors(origin: HyperPoint): Int = generatePoints(origin)
            .filter { it.isInGrid(grid.size, grid[0].size, grid[0][0].size, grid[0][0][0].size)}
            .filter {
                grid[it.w][it.z][it.y][it.x].isActive()
            }
            .count()

    private fun generatePoints(origin: HyperPoint): List<HyperPoint> {
        val points = mutableListOf<HyperPoint>()
        for (wrange in -1..1) {
            for (zrange in -1..1) {
                for (yrange in -1..1) {
                    for (xrange in -1..1) {
                        points.add(HyperPoint(origin.w + wrange, origin.z + zrange, origin.y + yrange, origin.x+xrange ))
                    }
                }
            }
        }

        return points.filter { it != origin }
    }

    data class HyperPoint(val w: Int, val z: Int, val y: Int, val x: Int) {
        fun isInGrid(ws: Int, zs: Int, ys:Int, xs:Int): Boolean {
            if (w == -1 || x == -1 || y == -1 || z==-1){
                return false
            } else return !(w >= ws || x >= xs || y >= ys || z >= zs)
        }
    }
}

class Cell(private var currentState: Char) {


    var pendingState: Char = currentState

    fun isActive(): Boolean {
        return currentState == ACTIVE
    }

    fun update(numActiveNeighbors: Int): Cell {
        if (isActive() && !(numActiveNeighbors in listOf(2,3))) {
            pendingState = INACTIVE
        } else if (numActiveNeighbors ==3) {
            pendingState = ACTIVE
        }
        return this
    }

    fun transition(): Boolean {
        val willTransition = pendingState != currentState
        currentState = pendingState
        return willTransition
    }

    override fun toString(): String {
        return "${currentState}"
    }
}
private const val INACTIVE = '.'
private const val ACTIVE = '#'
