package be.tabs_spaces.advent2022.days

class Day01: Day(1) {

    private val elvesInventory = inputString.split("\n\n")
        .map { elf -> elf.lines().map { line -> line.toInt() } }
        .map { it.sum() }

    override fun partOne() = elvesInventory.max()

    override fun partTwo() = elvesInventory
        .sortedDescending()
        .take(3)
        .sum()
}