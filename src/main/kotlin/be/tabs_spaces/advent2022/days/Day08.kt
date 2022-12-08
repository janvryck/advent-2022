package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.util.takeUntil

private typealias Forest = List<List<Char>>

class Day08 : Day(8) {

    private val forest: Forest = inputList.map { it.toList() }.also {
        require(it.size == it.first().size) { "Square forest required." }
    }

    override fun partOne() = forest.indices.map { x ->
        forest.indices.map { y ->
            forest.isVisible(x, y)
        }
    }.flatten().count { it }

    override fun partTwo() = forest.indices.map { x ->
        forest.indices.map { y ->
            forest.scenicScore(x, y)
        }
    }.flatten().max()

    private fun Forest.isVisible(x: Int, y: Int) = { it: Char -> it < this[x][y] }.let { areSmaller ->
        left(x, y).all(areSmaller)
                || right(x, y).all(areSmaller)
                || up(x, y).all(areSmaller)
                || down(x, y).all(areSmaller)
    }

    private fun Forest.scenicScore(x: Int, y: Int) = { it: Char -> it < this[x][y] }.let { viewBlocked ->
        left(x, y).reversed().takeUntil(viewBlocked).count() *
                right(x, y).takeUntil(viewBlocked).count() *
                up(x, y).reversed().takeUntil(viewBlocked).count() *
                down(x, y).takeUntil(viewBlocked).count()
    }

    private fun Forest.left(x: Int, y: Int) = (0 until x).map { this[it][y] }

    private fun Forest.right(x: Int, y: Int) = (x + 1 until size).map { this[it][y] }

    private fun Forest.up(x: Int, y: Int) = (0 until y).map { this[x][it] }

    private fun Forest.down(x: Int, y: Int) = (y + 1 until size).map { this[x][it] }

}
