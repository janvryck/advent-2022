package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.util.Point
import be.tabs_spaces.advent2022.util.Point.Companion.expand
import be.tabs_spaces.advent2022.util.Point.Companion.parse

class Day14 : Day(14) {
    private val regolith = inputList
        .map { it.split(" -> ") }
        .flatMap { it.zipWithNext() }
        .map { (first, second) -> parse(first) to parse(second) }
        .flatMap { it.expand() }
        .distinct()

    override fun partOne() = Cave(regolith).sandIntoTheAbyss()

    override fun partTwo() = Cave(regolith).fillWithSand(floorLevel = regolith.maxOf { it.y } + 2)

    private class Cave(regolith: List<Point>) {
        private val dropDirections = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))
        private val blocked = regolith.toMutableSet()
        private val sandSpill = Point(500, 0)
        private var grains = 0

        fun sandIntoTheAbyss() = numberOfGrains(
            isLast = { next, occupied -> next.y >= occupied.maxOf { it.y } },
            canMove = { next, occupied -> next !in occupied }
        )

        fun fillWithSand(floorLevel: Int) = numberOfGrains(
            isLast = { _, occupied -> occupied.contains(sandSpill) },
            canMove = { next, occupied -> next !in occupied && next.y < floorLevel }
        )

        private fun numberOfGrains(
            isLast: (Point, Set<Point>) -> Boolean,
            canMove: (Point, Set<Point>) -> Boolean,
        ): Int {
            do {
                val lastGrain = drop(sandSpill, isLast, canMove)
            } while (!lastGrain)
            return grains
        }

        private fun drop(
            start: Point,
            isLast: (Point, Set<Point>) -> Boolean,
            canMove: (Point, Set<Point>) -> Boolean,
        ): Boolean = start
            .takeIf { !isLast(it, blocked) }
            ?.let {
                dropDirections
                    .map { direction -> start.move(direction) }
                    .firstOrNull { canMove(it, blocked) }
                    ?.let { drop(it, isLast, canMove) }
                    ?: !blocked.add(start).also { grains++ }
            } ?: true
    }
}