package pember.aoc2022

class TuningTrouble: Aoc2022() {

    fun detectMarkerFromFile(fileName: String): Int =
        detectMarker(this.reader.readNonEmptyLines(fileName).first())

    fun detectMarker(signal: String): Int {
        return scanSignal(signal, MARKER_SIZE)
    }

    fun detectMessageFromFile(fileName: String): Int =
        detectMessage(this.reader.readNonEmptyLines(fileName).first())

    fun detectMessage(signal: String): Int {
        return scanSignal(signal, MESSAGE_SIZE)
    }

    private fun scanSignal(signal: String, targetSize: Int): Int =
        signal.windowed(targetSize, 1, false)
            .mapIndexedNotNull { index, s ->
                if (s.toCharArray().toSet().size == targetSize) {
                    index + targetSize
                } else {
                    null
                }
            }
            .first()

    companion object {
        private const val MARKER_SIZE = 4
        private const val MESSAGE_SIZE = 14
    }
}