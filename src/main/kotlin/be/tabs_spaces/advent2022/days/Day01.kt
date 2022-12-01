package be.tabs_spaces.advent2022.days

class Day01: Day(1) {

    private val inventory = inputString.split("\n\n")
        .map { it.lines().map { l -> l.toInt() } }

    override fun partOne() = inventory.maxOf { it.sum() }

    override fun partTwo() = inventory
        .map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}