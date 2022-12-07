package pember.aoc2022

class NoSpaceLeft(private val fileName: String): Aoc2022() {

    private val scanner = SystemScanner(null)

    init {
        reader.readNonEmptyLines(fileName)
            .forEach { scanner.accept(it.split(" ")) }
    }

    fun findSpaceToClear(): Long {
        return scanner.calculateSizes()
            .filter { it.second < 100000 }
            .sumOf { it.second }
    }

    fun findSmallestDir(): Long {
        val sizes = scanner.calculateSizes()
        val rootSize = sizes.find { it.first == "" }!!.second
        val toFind = 30000000 - (70000000L - rootSize)
        println("rootSize is ${rootSize}, which means I have free space ${70000000L - rootSize}")
        println("Need to find ${toFind} space")
        return sizes.filter { it.second >= toFind }
            .map { it.second }.minOf { it }
    }

    private class SystemScanner(
        private var currentDirectory: Directory?,
    ) {
        lateinit var root: Directory

        fun accept(input: List<String>) {
            if (input.first() == CMD) {
                handle(input)
            }  else {
                currentDirectory?.handleLs(input.first(), input.last())
            }
        }

        fun pwd(): String {
            val tokens = mutableListOf<String>()
            var tracked: Directory? = currentDirectory
            while(tracked != null) {
                tokens.add(tracked.name)
                tracked = tracked.parent
            }
            return tokens.joinToString("")
        }

        fun calculateSizes(): MutableList<Pair<String, Long>> {
            val sizes = mutableListOf<Pair<String, Long>>()
            root.recordSize(sizes)
            return sizes
        }

        private fun handle(tokens: List<String>) {
            if (tokens[1].toLowerCase() == "cd") {
               changeDirectory(tokens.last().toLowerCase())
            }
        }

        private fun changeDirectory(dir: String) {
            when (dir) {
                "/" -> {
                    currentDirectory = Directory("", parent = null)
                    root = currentDirectory!!
                }
                ".." -> {
                    currentDirectory = currentDirectory?.parent
                }
                else -> {
                    currentDirectory = currentDirectory?.findDir(dir)
                }
            }
        }

        companion object {
            private const val CMD = "$"
        }
    }

    private class Directory(val name: String,
                            val parent: Directory?,
                            val subdirs: MutableList<Directory> = mutableListOf(),
                            val files: MutableList<File> = mutableListOf()
    ) {
        fun findDir(name:String): Directory {
            return subdirs.first { it.name == name }
        }
        fun handleLs(l: String, r: String) {
            if (l == "dir") {
                this.subdirs.add(Directory(r, this))
            } else {
                this.files.add(File(r, l.toLong()))
            }
        }

        fun recordSize(sizes: MutableList<Pair<String, Long>>): Long {
            // calculate sizes of sub dir
            // each sub dir has a size. My size is my files plus my children
            var mysize: Long = 0L
            this.subdirs.forEach {dir ->
                mysize += dir.recordSize(sizes)
            }
            this.files.forEach {file ->
                mysize += file.size
            }
            sizes.add(this.name to mysize)
            return mysize
        }
    }

    private data class File(val name: String, val size: Long)
}