package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.util.Point
import java.util.*


class Day12 : Day(12) {

    private val hill = Hill.parse(inputString)

    override fun partOne() = hill.climb(hill.start)

    override fun partTwo() = hill.shortestRoute()

    private class Hill(
        val start: Point,
        val end: Point,
        val heightMap: Map<Point, Int>
    ) {
        companion object {
            fun parse(input: String): Hill {
                var start = Point(-1, -1)
                var end = Point(-1, -1)
                val heightMap = input.lines().flatMapIndexed { x, row ->
                    row.mapIndexed { y, c ->
                        when (c) {
                            'S' -> (Point(x, y) to 0).also { (point, _) -> start = point }
                            'E' -> (Point(x, y) to 25).also { (point, _) -> end = point }
                            else -> (Point(x, y) to c.code - 97)
                        }
                    }
                }.toMap()
                return Hill(start, end, heightMap)
            }
        }

        fun shortestRoute() = heightMap
            .filter { it.value == 0 }
            .keys
            .minOf { climb(it) }

        fun climb(start: Point): Int {
            val steps = mutableMapOf<Point, Int>().apply { put(start, 0) }
            val queue = LinkedList<Point>().apply { add(start) }
            val parent = mutableMapOf<Point, Point>()

            var current: Point?
            while (queue.poll().let { current = it; it != null && it != end }) {
                current!!.orthogonalNeighbours()
                    .filter { it in heightMap.keys }
                    .filter { heightMap.getValue(it) <= heightMap.getValue(current!!) + 1 }
                    .forEach { neighbour ->
                        val stepsToNeighbour = steps.getValue(current!!) + 1
                        if (stepsToNeighbour < steps.getOrDefault(neighbour, Int.MAX_VALUE)) {
                            steps[neighbour] = stepsToNeighbour
                            parent[neighbour] = current!!
                            queue.add(neighbour)
                        }
                    }
            }
            return parent.pathLengthFor(current)
        }

        private fun Map<Point, Point>.pathLengthFor(
            start: Point?
        ) = start
            ?.takeIf { it == end }
            ?.let {
                var current = it
                val path = mutableListOf<Point>()
                while (containsKey(current)) {
                    path.add(current)
                    current = getValue(current)
                }
                path.size
            } ?: Int.MAX_VALUE
    }
}