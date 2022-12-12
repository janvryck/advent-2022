package be.tabs_spaces.advent2022.util

import kotlin.math.abs
import kotlin.math.sign

data class Point(
    val x: Int = 0,
    val y: Int = 0
) {
    fun move(direction: Direction) = move(direction.dX, direction.dY)

    fun moveTowards(to: Point) = move((to.x - x).sign, (to.y - y).sign)

    private fun move(dX: Int, dY: Int) = Point(x + dX, y + dY)

    fun neighbours(other: Point) = abs(other.x - x) <= 1 && abs(other.y - y) <= 1

    fun orthogonalNeighbours(): List<Point> = listOf(
        Point(x - 1, y),
        Point(x + 1, y),
        Point(x, y - 1),
        Point(x, y + 1),
    )
}

enum class Direction(val dX: Int, val dY: Int) {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0)
    ;

    companion object {
        fun fromURDL(dir: String) = when (dir) {
            "U" -> NORTH
            "R" -> EAST
            "D" -> SOUTH
            "L" -> WEST
            else -> throw IllegalArgumentException("Only U, R, D and L are supported, provided: '$dir'.")
        }
    }
}