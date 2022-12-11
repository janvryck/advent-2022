package be.tabs_spaces.advent2022.util

fun greatestCommonDivisor(a: Int, b: Int): Int = when (b) {
    0 -> a
    else -> greatestCommonDivisor(b, a % b)
}

fun lowestCommonMultiple(a: Int, b: Int): Int =
    (a * b) / greatestCommonDivisor(a, b)