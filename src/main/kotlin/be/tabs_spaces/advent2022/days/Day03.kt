package be.tabs_spaces.advent2022.days

private const val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

class Day03 : Day(3) {

    override fun partOne(): Any {
        return inputList
            .map { it.chunked(it.length/2) }
            .flatMap { (first, second) -> first.toSet().intersect(second.toSet()) }
            .sumOf { alphabet.indexOf(it) + 1 }
    }

    override fun partTwo(): Any {
        return inputList
            .map { it.toSet() }
            .chunked(3)
            .flatMap { (first, second, third) -> first.intersect(second).intersect(third) }
            .sumOf { alphabet.indexOf(it) + 1 }
    }

}
