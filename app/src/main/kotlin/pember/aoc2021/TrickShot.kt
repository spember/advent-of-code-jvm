package pember.aoc2021

// Day 17
object TrickShot {

    fun hitsY(targetArea: TargetArea): Int {
        // fastest it can go without overshooting.. so we want it screaming down and not overshoot
        // because of the -1 each step, same velocity when coming down at 0 as we started
        // so fire upwards at positive value of negative lower bound, it hits max speed at 0, then next step is the
        // bottom of our target area
        val initialVelocity = (targetArea.second.first * -1) - 1
        println("starting with ${initialVelocity}")

        // gross. I bet there's math for this
        var v = initialVelocity
        var position = 0
        while (true) {
            val nextPosition = position + v
            v -= 1
            if (nextPosition < position) {
                break
            } else {
                position = nextPosition
            }
        }
        return position
    }
}

typealias TargetArea = Pair<Pair<Int,Int>, Pair<Int,Int>>