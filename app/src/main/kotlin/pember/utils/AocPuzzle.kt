package pember.utils

open class AocPuzzle(private val fileName: String, private val year: Int) {

    protected fun reader(): YearlyFileReader {
        return YearlyFileReader(year)
    }

    protected fun readLines(): Sequence<String> = reader().readNonEmptyLines(fileName)
}

open class AocPuzzle21(fileName: String): AocPuzzle(fileName, 2021)