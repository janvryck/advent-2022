package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.util.Direction
import be.tabs_spaces.advent2022.util.Point

class Day09 : Day(9) {

    private val bridgeMovements = inputList
        .map { it.split(" ") }
        .map { (d, r) -> Direction.fromURDL(d) to r.toInt() }

    override fun partOne() = Bridge(bridgeMovements).simulateRope(2)

    override fun partTwo() = Bridge(bridgeMovements).simulateRope(10)

    class Bridge(private val movements: List<Pair<Direction, Int>>) {
        fun simulateRope(numberOfKnots: Int) = with(mutableSetOf<Point>()) {
            val rope = MutableList(numberOfKnots) { Point() }
            movements.forEach { (direction, times) ->
                repeat(times) {
                    rope[0] = rope[0].move(direction)
                    rope.indices.zipWithNext().forEach { (head, tail) ->
                        if (!rope[tail].neighbours(rope[head])) {
                            rope[tail] = rope[tail].moveTowards(rope[head])
                        }
                    }
                    add(rope.last())
                }
            }
            size
        }
    }
}